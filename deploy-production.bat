@echo -----------------------------------------------------------------------------
@echo 生产环境将showcase${version}.jar发布到maven仓库中
@echo -----------------------------------------------------------------------------
mvn clean deploy -Dmaven.test.skip=true -Pprodjuction
@pause