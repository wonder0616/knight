package org.jeecg.modules.tpm.constant;

public interface CommonConstant
{
    enum SourceOfFailure {
        SPOTCHECK(1,"点检"),TPM(2,"TPM"),MES(3,"MES")
        ,INSPECTION(4,"AR巡检"),MAINTAIN(5,"保养");

        Integer id;
        String name;
        SourceOfFailure(Integer id,String name){
            this.id = id;
            this.name = name;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
