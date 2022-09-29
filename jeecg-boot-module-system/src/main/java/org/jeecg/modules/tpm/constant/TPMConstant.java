package org.jeecg.modules.tpm.constant;

public interface TPMConstant {
    //文件关联表-状态
    enum FileRelationStatus {
        NORMAL(1,"normal"),DEACTIVATE(9,"deactivate");

        Integer id;
        String name;
        FileRelationStatus(Integer id,String name){
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

    enum FileRelationType {
        maintenanceTools,maintenanceManual,knowledgeBase,spotCheckRecord,spotCheckRecordImg,spotCheckRecordVideo,inspectionRecord,maintenanceRecord,maintenancePhoto,maintenanceVoice,maintenanceVideo,officeDeviceFileRelation,auxiliaryDeviceFileRelation,specicalDeviceFileRelation;
    }

    enum DeviceTransferStatus {
        DRAFT(0,"draft"),PENDING(1,"pending");

        Integer id;
        String name;
        DeviceTransferStatus(Integer id,String name){
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
