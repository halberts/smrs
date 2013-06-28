package com.jof.framework.dao.hibernate.support;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.hibernate.util.xml.MappingReader;
import org.hibernate.util.xml.OriginImpl;
import org.hibernate.util.xml.XmlDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

import com.haier.openplatform.SysException;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateScalarModel;

/**
 * @author WangXuzheng
 *
 */
public class DefaultDynamicHibernateStatementBuilder implements DynamicHibernateStatementBuilder, ResourceLoaderAware,InitializingBean {
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultDynamicHibernateStatementBuilder.class);
	private Map<String, DynamicQuery> namedDynamicQuery;
	private String[] fileNames = new String[0];
	private ResourceLoader resourceLoader;
	private EntityResolver entityResolver = new DynamicStatementDTDEntityResolver();
	/**
	 * 查询语句名称缓存，不允许重复
	 */
	private Set<String> nameCache = new HashSet<String>();
	private Map<String, StatementTemplate> templateCache;
	private Configuration configuration;

	public void setFileNames(String[] fileNames) {
		this.fileNames = fileNames;
	}

	@Override
	public Map<String, DynamicQuery> getNamedDynamicQueries() {
		return namedDynamicQuery;
	}
	
	@Override
	public Map<String, StatementTemplate> getTemplateCache() {
		return templateCache;
	}

	private void initResources() throws IOException {
		namedDynamicQuery = new HashMap<String, DynamicQuery>();
		boolean flag = this.resourceLoader instanceof ResourcePatternResolver;
		for (String file : fileNames) {
			if (flag) {
				Resource[] resources = ((ResourcePatternResolver) this.resourceLoader).getResources(file);
				buildMap(resources);
			} else {
				Resource resource = resourceLoader.getResource(file);
				buildMap(resource);
			}
		}
		//clear name cache
		nameCache.clear();
	}

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	private void buildMap(Resource[] resources) throws IOException {
		if (resources == null) {
			return;
		}
		for (Resource resource : resources) {
			buildMap(resource);
		}
	}

	@SuppressWarnings({ "rawtypes" })
	private void buildMap(Resource resource) {
		InputSource inputSource = null;
		try {
			inputSource = new InputSource(resource.getInputStream());
			XmlDocument metadataXml = MappingReader.INSTANCE.readMappingDocument(entityResolver, inputSource,
					new OriginImpl("file", resource.getFilename()));
			if (isDynamicStatementXml(metadataXml)) {
				final Document doc = metadataXml.getDocumentTree();
				final Element dynamicHibernateStatement = doc.getRootElement();
				Iterator rootChildren = dynamicHibernateStatement.elementIterator();
				while (rootChildren.hasNext()) {
					final Element element = (Element) rootChildren.next();
					final String elementName = element.getName();
					if ("sql-query".equals(elementName)) {
						DynamicSqlQuery sqlQuery = new DynamicSqlQuery();
						putStatementToCacheMap(resource, element, sqlQuery);
					} else if ("hql-query".equals(elementName)) {
						DynamicHqlQuery hqlQuery = new DynamicHqlQuery();
						putStatementToCacheMap(resource, element, hqlQuery);
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error(e.toString());
			throw new SysException(e);
		} finally {
			if (inputSource != null && inputSource.getByteStream() != null) {
				try {
					inputSource.getByteStream().close();
				} catch (IOException e) {
					LOGGER.error(e.toString());
					throw new SysException(e);
				}
			}
		}

	}

	private void putStatementToCacheMap(Resource resource, final Element element, DynamicQuery dynamicQuery)
			throws IOException, ClassNotFoundException {
		String sqlQueryName = element.attribute("name").getText();
		Validate.notEmpty(sqlQueryName);
		if (nameCache.contains(sqlQueryName)) {
			throw new SysException("重复的sql-query/hql-query语句定义在文件:" + resource.getURI() + "中，必须保证name的唯一.");
		}
		nameCache.add(sqlQueryName);
		String queryText = element.getText();
		dynamicQuery.setQueryString(queryText);
		dynamicQuery.setQueryName(sqlQueryName);
		dynamicQuery.setCacheable("true".equals(emptyStringIfNull(element,"cacheable")));
		dynamicQuery.setCacheRegion(StringUtils.defaultIfBlank(emptyStringIfNull(element, "cacheRegion"),sqlQueryName));
		Element returnElement = element.element("return");
		if(returnElement != null){
			dynamicQuery.getReturnMapping().setClazz(ClassUtils.getClass(returnElement.attribute("class").getText()));
			@SuppressWarnings("unchecked")
			Iterator<Element> elementIterator = returnElement.elementIterator("return-property");
			while(elementIterator.hasNext()){
				ReturnProperty returnProperty = new ReturnProperty();
				Element returnPropertyElement = elementIterator.next();
				returnProperty.setColumn(returnPropertyElement.attribute("column").getText());
				returnProperty.setName(returnPropertyElement.attribute("name").getText());
				returnProperty.setType(returnPropertyElement.attribute("type").getText());
				dynamicQuery.getReturnMapping().getReturnProperties().add(returnProperty);
			}
		}
		namedDynamicQuery.put(sqlQueryName, dynamicQuery);
	}
	
	private String emptyStringIfNull(Element element,String attribute){
		Attribute attr = element.attribute(attribute);
		return attr == null? "" : attr.getText();
	}

	private static boolean isDynamicStatementXml(XmlDocument xmlDocument) {
		return "dynamic-hibernate-statement".equals(xmlDocument.getDocumentTree().getRootElement().getName());
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initResources();
		templateCache = new HashMap<String, StatementTemplate>();
		Map<String,DynamicQuery> namedQueries = this.getNamedDynamicQueries();
		Configuration configuration = getConfigurationInstance();
		StringTemplateLoader stringLoader = new StringTemplateLoader();
		for(Entry<String, DynamicQuery> entry : namedQueries.entrySet()){
			stringLoader.putTemplate(entry.getKey(), entry.getValue().getQueryString());
			DynamicQuery dynamicQuery = entry.getValue();
			templateCache.put(entry.getKey(), new StatementTemplate(new Template(entry.getKey(),new StringReader(entry.getValue().getQueryString()),configuration),dynamicQuery));
		}
		configuration.setTemplateLoader(stringLoader);
	}
	
	/**
	 * 创建Configuration实例,如果对Configuration有其他的属性配置,可以重写此方法
	 * @return Configuration
	 */
	protected Configuration getConfigurationInstance(){
		if(this.configuration == null){
			configuration = new Configuration();
			configuration.setNumberFormat("0.######");
			configuration.setObjectWrapper(new SQLStringWrapper());
		}
		return configuration;
	}
	
	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	protected static class SQLStringWrapper extends DefaultObjectWrapper{
		@Override
		public TemplateModel wrap(Object obj) throws TemplateModelException {
			 if (obj == null) {
				 return super.wrap(null);
		     }
	         if (obj instanceof TemplateModel) {
	            return (TemplateModel) obj;
	         }
	         if(obj instanceof String){
	        	 return new SQLStringScalar((String)obj);
	         }
	         return super.wrap(obj);
		}
	}
	
	protected static class SQLStringScalar implements TemplateScalarModel, Serializable{
		private static final long serialVersionUID = 4877138749481363872L;
		/**
	     * @serial the value of this <tt>SimpleScalar</tt> if it wraps a
	     * <tt>String</tt>.
	     */
	    private String value;

	    /**
	     * Constructs a <tt>SimpleScalar</tt> containing a string value.
	     * @param value the string value.
	     */
	    public SQLStringScalar(String value) {
	        this.value = StringEscapeUtils.escapeSql(value);
	    }

	    public String getAsString() {
	        return (value == null) ? "" : value;
	    }

	    public String toString() {
	        return value;
	    }
	}
}
