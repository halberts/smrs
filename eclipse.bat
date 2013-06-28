@echo -----------------------------------------------------------------------------
@echo 生成eclipse工程
@echo -----------------------------------------------------------------------------
mvn eclipse:clean eclipse:eclipse -DdownloadSources=true
@pause