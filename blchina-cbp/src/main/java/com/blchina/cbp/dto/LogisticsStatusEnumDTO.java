package com.blchina.cbp.dto;

/**
 * Created by admin on 2017/11/22.
 */
public class LogisticsStatusEnumDTO {

    private String status;
    private String date;
    private String sequence;
    private String cartype;
    public LogisticsStatusEnumDTO() {
    }

    public LogisticsStatusEnumDTO(String status, String date,String sequence,String cartype) {
        this.status = status;
        this.date = date;
        this.sequence = sequence;
        this.cartype=cartype;
    }

    public String getCartype() {
        return cartype;
    }

    public void setCartype(String cartype) {
        this.cartype = cartype;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

   public String getDate() {
      return date;
   }

   public void setDate(String date) {
      this.date = date;
   }

   public String getSequence() {
      return sequence;
   }

   public void setSequence(String sequence) {
      this.sequence = sequence;
   }
   
   

    
}
