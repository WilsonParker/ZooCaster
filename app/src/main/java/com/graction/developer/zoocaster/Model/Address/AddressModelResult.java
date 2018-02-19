package com.graction.developer.zoocaster.Model.Address;

import com.graction.developer.zoocaster.Interface.ListItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Graction06 on 2018-02-06.
 * Powered by 행정안정부
 *
 * @url : https://www.juso.go.kr/addrlink/main.do
 */

public class AddressModelResult {
    public static final int CountPerPage = 10;
    private AddressModel results;

    public static Map<String, String> getParameter(String keyword, int currentPage) {
        Map<String, String> params = new HashMap<>();
        params.put("keyword", keyword);
        params.put("resultType", "json");
        params.put("confmKey", "U01TX0FVVEgyMDE4MDIwNjE2MTYzNDEwNzY1NTk=");
        params.put("currentPage", currentPage + "");
        params.put("countPerPage", CountPerPage + "");
        return params;
    }

    public AddressModel getResults() {
        return results;
    }

    public void setResults(AddressModel results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "AddressModelResult{" +
                "results=" + results +
                '}';
    }

    public class AddressModel {
        private ArrayList<Juso> juso;
        private Common common;

        public ArrayList<Juso> getJuso() {
            return juso;
        }

        public void setJuso(ArrayList<Juso> juso) {
            this.juso = juso;
        }

        public Common getCommon() {
            return common;
        }

        public void setCommon(Common common) {
            this.common = common;
        }

        @Override
        public String toString() {
            return "AddressModelResult{" +
//                "juso=" + StringUtil.arrayToString(juso) +
                    "juso=" + juso +
                    ", common=" + common +
                    '}';
        }

        public class Common {
            private String errorMessage, countPerPage, totalCount, errorCode, currentPage;

            public String getErrorMessage() {
                return errorMessage;
            }

            public void setErrorMessage(String errorMessage) {
                this.errorMessage = errorMessage;
            }

            public String getCountPerPage() {
                return countPerPage;
            }

            public void setCountPerPage(String countPerPage) {
                this.countPerPage = countPerPage;
            }

            public String getTotalCount() {
                return totalCount;
            }

            public void setTotalCount(String totalCount) {
                this.totalCount = totalCount;
            }

            public String getErrorCode() {
                return errorCode;
            }

            public void setErrorCode(String errorCode) {
                this.errorCode = errorCode;
            }

            public String getCurrentPage() {
                return currentPage;
            }

            public void setCurrentPage(String currentPage) {
                this.currentPage = currentPage;
            }

            @Override
            public String toString() {
                return "Common{" +
                        "errorMessage='" + errorMessage + '\'' +
                        ", countPerPage='" + countPerPage + '\'' +
                        ", totalCount='" + totalCount + '\'' +
                        ", errorCode='" + errorCode + '\'' +
                        ", currentPage='" + currentPage + '\'' +
                        '}';
            }
        }

        public class Juso implements ListItem {
            private String detBdNmList, engAddr, rn, emdNm, ripNo, roadAddrPart2, emdNo, sggNm, jibunAddr, siNm, roadAddrPart1, bdNm, admCd, udrtYn, InbrMnnm, roadAddr, InbrSIno, buldMnnm, bdKdcd, liNm, rnMgtSn, mtYn, bdMgtSn, buldSIno;

            public String getDetBdNmList() {
                return detBdNmList;
            }

            public void setDetBdNmList(String detBdNmList) {
                this.detBdNmList = detBdNmList;
            }

            public String getEngAddr() {
                return engAddr;
            }

            public void setEngAddr(String engAddr) {
                this.engAddr = engAddr;
            }

            public String getRn() {
                return rn;
            }

            public void setRn(String rn) {
                this.rn = rn;
            }

            public String getEmdNm() {
                return emdNm;
            }

            public void setEmdNm(String emdNm) {
                this.emdNm = emdNm;
            }

            public String getRipNo() {
                return ripNo;
            }

            public void setRipNo(String ripNo) {
                this.ripNo = ripNo;
            }

            public String getRoadAddrPart2() {
                return roadAddrPart2;
            }

            public void setRoadAddrPart2(String roadAddrPart2) {
                this.roadAddrPart2 = roadAddrPart2;
            }

            public String getEmdNo() {
                return emdNo;
            }

            public void setEmdNo(String emdNo) {
                this.emdNo = emdNo;
            }

            public String getSggNm() {
                return sggNm;
            }

            public void setSggNm(String sggNm) {
                this.sggNm = sggNm;
            }

            public String getJibunAddr() {
                return jibunAddr;
            }

            public void setJibunAddr(String jibunAddr) {
                this.jibunAddr = jibunAddr;
            }

            public String getSiNm() {
                return siNm;
            }

            public void setSiNm(String siNm) {
                this.siNm = siNm;
            }

            public String getRoadAddrPart1() {
                return roadAddrPart1;
            }

            public void setRoadAddrPart1(String roadAddrPart1) {
                this.roadAddrPart1 = roadAddrPart1;
            }

            public String getBdNm() {
                return bdNm;
            }

            public void setBdNm(String bdNm) {
                this.bdNm = bdNm;
            }

            public String getAdmCd() {
                return admCd;
            }

            public void setAdmCd(String admCd) {
                this.admCd = admCd;
            }

            public String getUdrtYn() {
                return udrtYn;
            }

            public void setUdrtYn(String udrtYn) {
                this.udrtYn = udrtYn;
            }

            public String getInbrMnnm() {
                return InbrMnnm;
            }

            public void setInbrMnnm(String inbrMnnm) {
                InbrMnnm = inbrMnnm;
            }

            public String getRoadAddr() {
                return roadAddr;
            }

            public void setRoadAddr(String roadAddr) {
                this.roadAddr = roadAddr;
            }

            public String getInbrSIno() {
                return InbrSIno;
            }

            public void setInbrSIno(String inbrSIno) {
                InbrSIno = inbrSIno;
            }

            public String getBuldMnnm() {
                return buldMnnm;
            }

            public void setBuldMnnm(String buldMnnm) {
                this.buldMnnm = buldMnnm;
            }

            public String getBdKdcd() {
                return bdKdcd;
            }

            public void setBdKdcd(String bdKdcd) {
                this.bdKdcd = bdKdcd;
            }

            public String getLiNm() {
                return liNm;
            }

            public void setLiNm(String liNm) {
                this.liNm = liNm;
            }

            public String getRnMgtSn() {
                return rnMgtSn;
            }

            public void setRnMgtSn(String rnMgtSn) {
                this.rnMgtSn = rnMgtSn;
            }

            public String getMtYn() {
                return mtYn;
            }

            public void setMtYn(String mtYn) {
                this.mtYn = mtYn;
            }

            public String getBdMgtSn() {
                return bdMgtSn;
            }

            public void setBdMgtSn(String bdMgtSn) {
                this.bdMgtSn = bdMgtSn;
            }

            public String getBuldSIno() {
                return buldSIno;
            }

            public void setBuldSIno(String buldSIno) {
                this.buldSIno = buldSIno;
            }

            @Override
            public String toString() {
                return "Juso{" +
                        "detBdNmList='" + detBdNmList + '\'' +
                        ", engAddr='" + engAddr + '\'' +
                        ", rn='" + rn + '\'' +
                        ", emdNm='" + emdNm + '\'' +
                        ", ripNo='" + ripNo + '\'' +
                        ", roadAddrPart2='" + roadAddrPart2 + '\'' +
                        ", emdNo='" + emdNo + '\'' +
                        ", sggNm='" + sggNm + '\'' +
                        ", jibunAddr='" + jibunAddr + '\'' +
                        ", siNm='" + siNm + '\'' +
                        ", roadAddrPart1='" + roadAddrPart1 + '\'' +
                        ", bdNm='" + bdNm + '\'' +
                        ", admCd='" + admCd + '\'' +
                        ", udrtYn='" + udrtYn + '\'' +
                        ", InbrMnnm='" + InbrMnnm + '\'' +
                        ", roadAddr='" + roadAddr + '\'' +
                        ", InbrSIno='" + InbrSIno + '\'' +
                        ", buldMnnm='" + buldMnnm + '\'' +
                        ", bdKdcd='" + bdKdcd + '\'' +
                        ", liNm='" + liNm + '\'' +
                        ", rnMgtSn='" + rnMgtSn + '\'' +
                        ", mtYn='" + mtYn + '\'' +
                        ", bdMgtSn='" + bdMgtSn + '\'' +
                        ", buldSIno='" + buldSIno + '\'' +
                        '}';
            }
        }
    }

}
