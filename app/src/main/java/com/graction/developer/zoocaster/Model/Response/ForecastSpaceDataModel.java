package com.graction.developer.zoocaster.Model.Response;

import com.graction.developer.zoocaster.Util.StringUtil;

import java.util.ArrayList;

/*

POP 강수확률
PTY 강수형태
R06 6시간 강수량
REH 습도
S06 6시간 신적설
SKY 하늘상태
T3H 3시간 기온
TMN 아침 최저기온
TMX 낮 최고기온
UUU 풍속(동서성분)
VVV 풍속(남북성분)
WAV 파고
VEC 풍향
WSD 풍속

*/
public class ForecastSpaceDataModel {
    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public String getPop(){
        for(Response.Body.Items.Item item : getResponse().getBody().getItems().getItem()){
            if(item.category.equalsIgnoreCase("POP"))
                return item.getFcstValue();
        }
        return "-";
    }

    public String getR06(){
        for(Response.Body.Items.Item item : getResponse().getBody().getItems().getItem()){
            if(item.category.equalsIgnoreCase("R06"))
                return item.getFcstValue();
        }
        return "0";
    }

    @Override
    public String toString() {
        return "ForecastSpaceDataModel [response=" + response + "]";
    }

    public class Response {
        private Header header;
        private Body body;

        public Header getHeader() {
            return header;
        }

        public void setHeader(Header header) {
            this.header = header;
        }

        public Body getBody() {
            return body;
        }

        public void setBody(Body body) {
            this.body = body;
        }

        @Override
        public String toString() {
            return "Response [header=" + header + ", body=" + body + "]";
        }

        public class Header {
            private String resultCode, resultMsg;

            public String getResultCode() {
                return resultCode;
            }

            public void setResultCode(String resultCode) {
                this.resultCode = resultCode;
            }

            public String getResultMsg() {
                return resultMsg;
            }

            public void setResultMsg(String resultMsg) {
                this.resultMsg = resultMsg;
            }

            @Override
            public String toString() {
                return "Header [resultCode=" + resultCode + ", resultMsg=" + resultMsg + "]";
            }

        }

        public class Body {
            private int numOfRows, pageNo, totalCount;
            private Items items;

            public Items getItems() {
                return items;
            }

            public void setItems(Items items) {
                this.items = items;
            }

            public int getNumOfRows() {
                return numOfRows;
            }

            public void setNumOfRows(int numOfRows) {
                this.numOfRows = numOfRows;
            }

            public int getPageNo() {
                return pageNo;
            }

            public void setPageNo(int pageNo) {
                this.pageNo = pageNo;
            }

            public int getTotalCount() {
                return totalCount;
            }

            public void setTotalCount(int totalCount) {
                this.totalCount = totalCount;
            }

            @Override
            public String toString() {
                return "Body [numOfRows=" + numOfRows + ", pageNo=" + pageNo + ", totalCount=" + totalCount + ", items="
                        + items + "]";
            }

            public class Items {
                private ArrayList<Item> item;

                public ArrayList<Item> getItem() {
                    return item;
                }

                public void setItems(ArrayList<Item> item) {
                    this.item = item;
                }


                @Override
                public String toString() {
                    return "Items ["+StringUtil.createString("item", item)+ "]";
                }


                public class Item {
                    private String category;
                    private int baseDate, baseTime, fcstDate, fcstTime, nx, ny;
                    private String fcstValue;

                    public String getCategory() {
                        return category;
                    }

                    public void setCategory(String category) {
                        this.category = category;
                    }

                    public int getBaseDate() {
                        return baseDate;
                    }

                    public void setBaseDate(int baseDate) {
                        this.baseDate = baseDate;
                    }

                    public int getBaseTime() {
                        return baseTime;
                    }

                    public void setBaseTime(int baseTime) {
                        this.baseTime = baseTime;
                    }

                    public int getFcstDate() {
                        return fcstDate;
                    }

                    public void setFcstDate(int fcstDate) {
                        this.fcstDate = fcstDate;
                    }

                    public int getFcstTime() {
                        return fcstTime;
                    }

                    public void setFcstTime(int fcstTime) {
                        this.fcstTime = fcstTime;
                    }

                    public int getNx() {
                        return nx;
                    }

                    public void setNx(int nx) {
                        this.nx = nx;
                    }

                    public int getNy() {
                        return ny;
                    }

                    public void setNy(int ny) {
                        this.ny = ny;
                    }

                    public String getFcstValue() {
                        return fcstValue;
                    }

                    public void setFcstValue(String fcstValue) {
                        this.fcstValue = fcstValue;
                    }

                    @Override
                    public String toString() {
                        return "Item [baseDate=" + baseDate + ", baseTime=" + baseTime + ", category=" + category
                                + ", fcstDate=" + fcstDate + ", fcstTime=" + fcstTime + ", fcstValue=" + fcstValue
                                + ", nx=" + nx + ", ny=" + ny + "]";
                    }
                }
            }
        }
    }
}
