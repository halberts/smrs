@echo -----------------------------------------------------------------------------
@echo ����������showcase${version}.jar������maven�ֿ���
@echo -----------------------------------------------------------------------------
mvn clean deploy -Dmaven.test.skip=true -Pprodjuction
@pause