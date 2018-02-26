package com.graction.developer.zoocaster.Model.Response;

import com.graction.developer.zoocaster.Model.VO.FineDustVO;

import java.util.ArrayList;
import java.util.List;

/*
    Open Data API
	등급	좋음	보통	나쁨	매우나쁨
	Grade 값	1	2	3	4
*/
public class IntegratedAirQualityModel {
    public static final String TYPE_PM10 = "pm10", TYPE_PM25 = "pm2.5", TYPE_O3 = "o3";
    private List<IntegratedAirQualityModelItem> list;
    private IntegratedAirQualityModelItem parm, ArpltnInforInqireSvcVo;
    private int totalCount;

    public List<IntegratedAirQualityModelItem> getList() {
        return list;
    }

    public void setList(List<IntegratedAirQualityModelItem> list) {
        this.list = list;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public IntegratedAirQualityModelItem getParm() {
        return parm;
    }

    public void setParm(IntegratedAirQualityModelItem parm) {
        this.parm = parm;
    }

    public IntegratedAirQualityModelItem getArpltnInforInqireSvcVo() {
        return ArpltnInforInqireSvcVo;
    }

    public void setArpltnInforInqireSvcVo(IntegratedAirQualityModelItem arpltnInforInqireSvcVo) {
        ArpltnInforInqireSvcVo = arpltnInforInqireSvcVo;
    }

    @Override
    public String toString() {
        return "IntegratedAirQualityModel [list=" + list + ", parm=" + parm + ", ArpltnInforInqireSvcVo="
                + ArpltnInforInqireSvcVo + ", totalCount=" + totalCount + "]";
    }

    public IntegratedAirQualityModelItem getFirstItem() {
        if (list != null && list.size() > 0)
            return list.get(0);
        else
            return null;
    }

    public class IntegratedAirQualityModelItem {
        private ArrayList<FineDustVO> fineDustStandard;
        private String _returnType, dateTerm        // 날짜 기간
                , dataTime, mangName        // 측정망 정보
                , resultCode    // 결과 코드
                , resultMsg        // 결과 메세지
                , sidoName        // 시도 명
                , stationName    // 측정소 명
                , coValue        // 일산화탄소 농도 (단위 : ppm)
                , khaiValue        // 통합대기환경수치
                , no2Value        // 이산화질소 농도 (단위 : ppm)
                , o3Value        // 오존 농도 (단위 : ppm)
                , pm10Value        // 미세먼지 (PM10) 농도 (단위 : ㎍/m³)
                , pm10Value24    // 미세먼지 (PM10) 24시간 예측 이동 농도 (단위 : ㎍/m³)
                , pm25Value        // 초미세먼지 (PM2.5) 농도 (단위 : ㎍/m³)
                , pm25Value24    // 초미세먼지 (PM2.5) 24시간 예측 이동 농도 (단위 : ㎍/m³)
                , so2Value        // 아황산가스 농도 (단위 : ppm)

                , coGrade, khaiGrade        // 통합환경대기지수
                , no2Grade, o3Grade, pm10Grade, pm10Grade1h, pm25Grade, pm25Grade1h, so2Grade, stationCode, totalCount, rnum, pageNo;

        public static final String KEY_CO = "co", KEY_KHAI = "khai", KEY_O3 = "o3", KEY_PM10 = "pm10", KEY_PM25 = "pm25", KEY_SO2 = "so2", KEY_NO2 = "no2";

        public String get_returnType() {
            return _returnType;
        }


        public void set_returnType(String _returnType) {
            this._returnType = _returnType;
        }


        public String getDateTerm() {
            return dateTerm;
        }


        public void setDateTerm(String dateTerm) {
            this.dateTerm = dateTerm;
        }


        public String getDataTime() {
            return dataTime;
        }


        public void setDataTime(String dataTime) {
            this.dataTime = dataTime;
        }


        public String getMangName() {
            return mangName;
        }


        public void setMangName(String mangName) {
            this.mangName = mangName;
        }


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


        public String getSidoName() {
            return sidoName;
        }


        public void setSidoName(String sidoName) {
            this.sidoName = sidoName;
        }


        public String getStationName() {
            return stationName;
        }


        public void setStationName(String stationName) {
            this.stationName = stationName;
        }


        public String getCoValue() {
            return coValue;
        }


        public void setCoValue(String coValue) {
            this.coValue = coValue;
        }


        public String getKhaiValue() {
            return khaiValue;
        }


        public void setKhaiValue(String khaiValue) {
            this.khaiValue = khaiValue;
        }


        public String getO3Value() {
            return o3Value;
        }


        public void setO3Value(String o3Value) {
            this.o3Value = o3Value;
        }


        public String getPm10Value() {
            return pm10Value;
        }


        public void setPm10Value(String pm10Value) {
            this.pm10Value = pm10Value;
        }


        public String getPm10Value24() {
            return pm10Value24;
        }


        public void setPm10Value24(String pm10Value24) {
            this.pm10Value24 = pm10Value24;
        }


        public String getPm25Value() {
            return pm25Value;
        }


        public void setPm25Value(String pm25Value) {
            this.pm25Value = pm25Value;
        }


        public String getPm25Value24() {
            return pm25Value24;
        }


        public void setPm25Value24(String pm25Value24) {
            this.pm25Value24 = pm25Value24;
        }


        public String getSo2Value() {
            return so2Value;
        }


        public void setSo2Value(String so2Value) {
            this.so2Value = so2Value;
        }


        public String getCoGrade() {
            return coGrade;
        }


        public void setCoGrade(String coGrade) {
            this.coGrade = coGrade;
        }


        public String getKhaiGrade() {
            return khaiGrade;
        }


        public void setKhaiGrade(String khaiGrade) {
            this.khaiGrade = khaiGrade;
        }

        public String getNo2Value() {
            return no2Value;
        }

        public void setNo2Value(String no2Value) {
            this.no2Value = no2Value;
        }

        public String getNo2Grade() {
            return no2Grade;
        }

        public void setNo2Grade(String no2Grade) {
            this.no2Grade = no2Grade;
        }

        public String getPm10Grade() {
            return pm10Grade;
        }


        public void setPm10Grade(String pm10Grade) {
            this.pm10Grade = pm10Grade;
        }


        public String getPm10Grade1h() {
            return pm10Grade1h;
        }


        public void setPm10Grade1h(String pm10Grade1h) {
            this.pm10Grade1h = pm10Grade1h;
        }


        public String getPm25Grade() {
            return pm25Grade;
        }


        public void setPm25Grade(String pm25Grade) {
            this.pm25Grade = pm25Grade;
        }


        public String getPm25Grade1h() {
            return pm25Grade1h;
        }


        public void setPm25Grade1h(String pm25Grade1h) {
            this.pm25Grade1h = pm25Grade1h;
        }


        public String getSo2Grade() {
            return so2Grade;
        }


        public void setSo2Grade(String so2Grade) {
            this.so2Grade = so2Grade;
        }


        public String getStationCode() {
            return stationCode;
        }


        public void setStationCode(String stationCode) {
            this.stationCode = stationCode;
        }


        public String getTotalCount() {
            return totalCount;
        }


        public void setTotalCount(String totalCount) {
            this.totalCount = totalCount;
        }


        public String getRnum() {
            return rnum;
        }


        public void setRnum(String rnum) {
            this.rnum = rnum;
        }


        public String getPageNo() {
            return pageNo;
        }


        public void setPageNo(String pageNo) {
            this.pageNo = pageNo;
        }


        public String getO3Grade() {
            return o3Grade;
        }

        public void setO3Grade(String o3Grade) {
            this.o3Grade = o3Grade;
        }

        public ArrayList<FineDustVO> getFineDustStandard() {
            return fineDustStandard;
        }

        public void setFineDustStandard(ArrayList<FineDustVO> fineDustStandard) {
            this.fineDustStandard = fineDustStandard;
        }

        @Override
        public String toString() {
            return "IntegratedAirQualityModelItem{" +
                    "_returnType='" + _returnType + '\'' +
                    ", dateTerm='" + dateTerm + '\'' +
                    ", dataTime='" + dataTime + '\'' +
                    ", mangName='" + mangName + '\'' +
                    ", resultCode='" + resultCode + '\'' +
                    ", resultMsg='" + resultMsg + '\'' +
                    ", sidoName='" + sidoName + '\'' +
                    ", stationName='" + stationName + '\'' +
                    ", coValue='" + coValue + '\'' +
                    ", khaiValue='" + khaiValue + '\'' +
                    ", no2Value='" + no2Value + '\'' +
                    ", o3Value='" + o3Value + '\'' +
                    ", pm10Value='" + pm10Value + '\'' +
                    ", pm10Value24='" + pm10Value24 + '\'' +
                    ", pm25Value='" + pm25Value + '\'' +
                    ", pm25Value24='" + pm25Value24 + '\'' +
                    ", so2Value='" + so2Value + '\'' +
                    ", coGrade='" + coGrade + '\'' +
                    ", khaiGrade='" + khaiGrade + '\'' +
                    ", no2Grade='" + no2Grade + '\'' +
                    ", o3Grade='" + o3Grade + '\'' +
                    ", pm10Grade='" + pm10Grade + '\'' +
                    ", pm10Grade1h='" + pm10Grade1h + '\'' +
                    ", pm25Grade='" + pm25Grade + '\'' +
                    ", pm25Grade1h='" + pm25Grade1h + '\'' +
                    ", so2Grade='" + so2Grade + '\'' +
                    ", stationCode='" + stationCode + '\'' +
                    ", totalCount='" + totalCount + '\'' +
                    ", rnum='" + rnum + '\'' +
                    ", pageNo='" + pageNo + '\'' +
                    '}';
        }

        public String getGrade(String grade) {
            String result;
            switch (grade) {
                case "1":
                    result = "좋음";
                    break;
                case "2":
                    result = "보통";
                    break;
                case "3":
                    result = "나쁨";
                    break;
                case "4":
                default:
                    result = "매우나쁨";
                    break;
            }
            return result;
        }

        public String getGrade(String value, String type) {
            if (value != null && fineDustStandard != null) {
                int i = Integer.parseInt(value);
                for (FineDustVO vo : fineDustStandard) {
                    if (vo.getFineDust_type().equalsIgnoreCase(type) && vo.getFineDust_min() <= i && i <= vo.getFineDust_max()) {
                        return vo.getFineDust_grade();
                    }
                }
            }
            return "?";
        }

        public String gradeToStringForKey(String key) {
            String result;
            switch (key) {
                case KEY_CO:
                    result = getGrade(getCoGrade());
                    break;
                case KEY_KHAI:
                    result = getGrade(getKhaiGrade());
                    break;
                case KEY_PM10:
                    result = getGrade(getPm10Grade());
                    break;
                case KEY_PM25:
                    result = getGrade(getPm25Grade());
                    break;
                case KEY_O3:
                    result = getGrade(getO3Grade());
                    break;
                case KEY_SO2:
                    result = getGrade(getSo2Grade());
                    break;
                case KEY_NO2:
                    result = getGrade(getNo2Grade());
                    break;
                default:
                    result = getGrade("");
                    break;
            }
            return result;
        }

        public String getColor(String value) {
            if (value != null) {
                int i = Integer.parseInt(value);
                for (FineDustVO vo : fineDustStandard) {
                    if (vo.getFineDust_min() <= i && i <= vo.getFineDust_max()) {
                        return vo.getFineDust_color();
                    }
                }
            }
            return "#000000";
        }

        public String getColorForGrade(String grade) {
            if (grade != null) {
                for (FineDustVO vo : fineDustStandard) {
                    if (grade.equals(vo.getFineDust_grade())) {
                        return vo.getFineDust_color();
                    }
                }
            }
            return "#000000";
        }

        public String getColorForIntGrade(String grade) {
            if (grade != null) {
                String tGrade = getGrade(grade);
                for (FineDustVO vo : fineDustStandard) {
                    if (tGrade .equals(vo.getFineDust_grade())) {
                        return vo.getFineDust_color();
                    }
                }
            }
            return "#000000";
        }

    }

}