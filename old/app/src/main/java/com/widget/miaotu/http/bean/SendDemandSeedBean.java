package com.widget.miaotu.http.bean;

import java.util.List;

public class SendDemandSeedBean {
    /**
     * seedlingId : 5
     * name : 阿穆尔丁香
     * commonNames : 暴马丁香,暴马子,白丁香,荷花丁香
     * classifyFirsts :
     * [
     *
     * {"firstClassifyId":1,"seedlingId":5,"hasSecondClassify":1,"name":"乔木",
     * "classifySeconds":[
     * {"secondClassifyId":1,"seedlingId":5,"name":"单杆",
     * "specLists":[
     * {"specId":1,"name":"胸径","mustWrite":1,"unit":"厘米"},
     * {"specId":2,"name":"高度","mustWrite":1,"unit":"米"},
     * {"specId":3,"name":"冠幅","mustWrite":0,"unit":"米"},
     * {"specId":4,"name":"分支点","mustWrite":0,"unit":"米"},
     * {"specId":5,"name":"土球尺寸","mustWrite":0,"unit":"厘米"}
     * ]},
     * {"secondClassifyId":2,"seedlingId":5,"name":"多杆",
     * "specLists":[
     * {"specId":6,"name":"杆数","mustWrite":1,"unit":"杆"},
     * {"specId":7,"name":"杆径","mustWrite":1,"unit":"厘米"},
     * {"specId":12,"name":"地径","mustWrite":0,"unit":"厘米"},
     * {"specId":2,"name":"高度","mustWrite":0,"unit":"米"},
     * {"specId":3,"name":"冠幅","mustWrite":0,"unit":"米"},
     * {"specId":5,"name":"土球尺寸","mustWrite":0,"unit":"厘米"}
     * ]
     * }],
     *"plantTypeLists":[
     * {"plantTypeId":1,"name":"容器苗"},{"plantTypeId":2,"name":"移栽苗"},{"plantTypeId":3,"name":"地栽苗"}
     * ]
     * },

     {"firstClassifyId":2,"seedlingId":5,"hasSecondClassify":1,"name":"灌木",
     * "classifySeconds":[
     * {"secondClassifyId":3,"seedlingId":5,"name":"自然型",
     * "specLists":[{"specId":13,"name":"高度","mustWrite":1,"unit":"厘米"},{"specId":14,"name":"冠幅","mustWrite":1,"unit":"厘米"},{"specId":12,"name":"地径","mustWrite":0,"unit":"厘米"},{"specId":8,"name":"分枝数","mustWrite":0,"unit":"支"},{"specId":5,"name":"土球尺寸","mustWrite":0,"unit":"厘米"}]},{"secondClassifyId":4,"seedlingId":5,"name":"高桩","specLists":[{"specId":12,"name":"地径","mustWrite":1,"unit":"厘米"},{"specId":14,"name":"冠幅","mustWrite":1,"unit":"厘米"},{"specId":13,"name":"高度","mustWrite":0,"unit":"厘米"},{"specId":15,"name":"分支数","mustWrite":0,"unit":"厘米"},{"specId":5,"name":"土球尺寸","mustWrite":0,"unit":"厘米"}]}],"plantTypeLists":[{"plantTypeId":1,"name":"容器苗"},{"plantTypeId":2,"name":"移栽苗"},{"plantTypeId":3,"name":"地栽苗"},{"plantTypeId":4,"name":"盆苗"}]}]
     */
    private int seedlingId;
    private String name;
    private String commonNames;
    private List<ClassifyFirstsBean> classifyFirsts;

    @Override
    public String toString() {
        return "SendDemandSeedBean{" +
                "seedlingId=" + seedlingId +
                ", name='" + name + '\'' +
                ", commonNames='" + commonNames + '\'' +
                ", classifyFirsts=" + classifyFirsts +
                '}';
    }

    public int getSeedlingId() {
        return seedlingId;
    }

    public void setSeedlingId(int seedlingId) {
        this.seedlingId = seedlingId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommonNames() {
        return commonNames;
    }

    public void setCommonNames(String commonNames) {
        this.commonNames = commonNames;
    }

    public List<ClassifyFirstsBean> getClassifyFirsts() {
        return classifyFirsts;
    }

    public void setClassifyFirsts(List<ClassifyFirstsBean> classifyFirsts) {
        this.classifyFirsts = classifyFirsts;
    }

    public static class ClassifyFirstsBean {
        /**
         * firstClassifyId : 1
         * seedlingId : 5
         * hasSecondClassify : 1
         * name : 乔木
         * classifySeconds : [{"secondClassifyId":1,"seedlingId":5,"name":"单杆","specLists":[{"specId":1,"name":"胸径","mustWrite":1,"unit":"厘米"},{"specId":2,"name":"高度","mustWrite":1,"unit":"米"},{"specId":3,"name":"冠幅","mustWrite":0,"unit":"米"},{"specId":4,"name":"分支点","mustWrite":0,"unit":"米"},{"specId":5,"name":"土球尺寸","mustWrite":0,"unit":"厘米"}]},{"secondClassifyId":2,"seedlingId":5,"name":"多杆","specLists":[{"specId":6,"name":"杆数","mustWrite":1,"unit":"杆"},{"specId":7,"name":"杆径","mustWrite":1,"unit":"厘米"},{"specId":12,"name":"地径","mustWrite":0,"unit":"厘米"},{"specId":2,"name":"高度","mustWrite":0,"unit":"米"},{"specId":3,"name":"冠幅","mustWrite":0,"unit":"米"},{"specId":5,"name":"土球尺寸","mustWrite":0,"unit":"厘米"}]}]
         * plantTypeLists : [{"plantTypeId":1,"name":"容器苗"},{"plantTypeId":2,"name":"移栽苗"},{"plantTypeId":3,"name":"地栽苗"}]
         */

        private int firstClassifyId;
        private int seedlingId;
        private int hasSecondClassify;
        private String name;
        private List<ClassifySecondsBean> classifySeconds;
        private List<PlantTypeListsBean> plantTypeLists;

        private List<ClassifySecondsBean.SpecListsBean> specLists;

        private String isChose;

        @Override
        public String toString() {
            return "ClassifyFirstsBean{" +
                    "firstClassifyId=" + firstClassifyId +
                    ", seedlingId=" + seedlingId +
                    ", hasSecondClassify=" + hasSecondClassify +
                    ", name='" + name + '\'' +
                    ", classifySeconds=" + classifySeconds +
                    ", plantTypeLists=" + plantTypeLists +
                    ", specLists=" + specLists +
                    ", isChose='" + isChose + '\'' +
                    '}';
        }

        public String getIsChose() {
            return isChose;
        }

        public void setIsChose(String isChose) {
            this.isChose = isChose;
        }

        public List<ClassifySecondsBean.SpecListsBean> getSpecLists() {
            return specLists;
        }

        public void setSpecLists(List<ClassifySecondsBean.SpecListsBean> specLists) {
            this.specLists = specLists;
        }

        public int getFirstClassifyId() {
            return firstClassifyId;
        }

        public void setFirstClassifyId(int firstClassifyId) {
            this.firstClassifyId = firstClassifyId;
        }

        public int getSeedlingId() {
            return seedlingId;
        }

        public void setSeedlingId(int seedlingId) {
            this.seedlingId = seedlingId;
        }

        public int getHasSecondClassify() {
            return hasSecondClassify;
        }

        public void setHasSecondClassify(int hasSecondClassify) {
            this.hasSecondClassify = hasSecondClassify;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<ClassifySecondsBean> getClassifySeconds() {
            return classifySeconds;
        }

        public void setClassifySeconds(List<ClassifySecondsBean> classifySeconds) {
            this.classifySeconds = classifySeconds;
        }

        public List<PlantTypeListsBean> getPlantTypeLists() {
            return plantTypeLists;
        }

        public void setPlantTypeLists(List<PlantTypeListsBean> plantTypeLists) {
            this.plantTypeLists = plantTypeLists;
        }

        public static class ClassifySecondsBean {
            /**
             * secondClassifyId : 1
             * seedlingId : 5
             * name : 单杆
             * specLists : [{"specId":1,"name":"胸径","mustWrite":1,"unit":"厘米"},{"specId":2,"name":"高度","mustWrite":1,"unit":"米"},{"specId":3,"name":"冠幅","mustWrite":0,"unit":"米"},{"specId":4,"name":"分支点","mustWrite":0,"unit":"米"},{"specId":5,"name":"土球尺寸","mustWrite":0,"unit":"厘米"}]
             */

            private int secondClassifyId;
            private int seedlingId;
            private String name;
            private List<SpecListsBean> specLists;

            @Override
            public String toString() {
                return "ClassifySecondsBean{" +
                        "secondClassifyId=" + secondClassifyId +
                        ", seedlingId=" + seedlingId +
                        ", name='" + name + '\'' +
                        ", specLists=" + specLists +
                        ", isChose='" + isChose + '\'' +
                        '}';
            }

            public int getSecondClassifyId() {
                return secondClassifyId;
            }

            private String isChose;

            public String getIsChose() {
                return isChose;
            }

            public void setIsChose(String isChose) {
                this.isChose = isChose;
            }

            public void setSecondClassifyId(int secondClassifyId) {
                this.secondClassifyId = secondClassifyId;
            }

            public int getSeedlingId() {
                return seedlingId;
            }

            public void setSeedlingId(int seedlingId) {
                this.seedlingId = seedlingId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<SpecListsBean> getSpecLists() {
                return specLists;
            }

            public void setSpecLists(List<SpecListsBean> specLists) {
                this.specLists = specLists;
            }

            public static class SpecListsBean {
                /**
                 * specId : 1
                 * name : 胸径
                 * mustWrite : 1
                 * unit : 厘米
                 */

                private int specId;
                private String name;
                private int mustWrite;
                private String unit;

//自己加的字段
                private String interval;


                @Override
                public String toString() {
                    return "SpecListsBean{" +
                            "specId=" + specId +
                            ", name='" + name + '\'' +
                            ", mustWrite=" + mustWrite +
                            ", unit='" + unit + '\'' +
                            '}';
                }


                public String getInterval() {
                    return interval;
                }

                public void setInterval(String interval) {
                    this.interval = interval;
                }

                public int getSpecId() {
                    return specId;
                }

                public void setSpecId(int specId) {
                    this.specId = specId;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public int getMustWrite() {
                    return mustWrite;
                }

                public void setMustWrite(int mustWrite) {
                    this.mustWrite = mustWrite;
                }

                public String getUnit() {
                    return unit;
                }

                public void setUnit(String unit) {
                    this.unit = unit;
                }
            }
        }

        public static class PlantTypeListsBean {
            /**
             * plantTypeId : 1
             * name : 容器苗
             */

            private int plantTypeId;
            private String name;

            private String isChose;

            @Override
            public String toString() {
                return "PlantTypeListsBean{" +
                        "plantTypeId=" + plantTypeId +
                        ", name='" + name + '\'' +
                        ", isChose='" + isChose + '\'' +
                        '}';
            }

            public String getIsChose() {
                return isChose;
            }

            public void setIsChose(String isChose) {
                this.isChose = isChose;
            }

            public int getPlantTypeId() {
                return plantTypeId;
            }

            public void setPlantTypeId(int plantTypeId) {
                this.plantTypeId = plantTypeId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
