package vn.vnpt.tracebility_custom.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vn.vnpt.tracebility_custom.config.RedisInstance;
import vn.vnpt.tracebility_custom.entity.Partner;
import vn.vnpt.tracebility_custom.entity.UploadKey;
import vn.vnpt.tracebility_custom.repository.PartnerRepository;
import vn.vnpt.tracebility_custom.repository.UploadKeyRepository;
import vn.vnpt.tracebility_custom.util.ParseUtil;

import javax.annotation.PostConstruct;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class CheckService {

    @Autowired
    private RedisInstance redisInstance;

    @Autowired
    private PartnerRepository partnerRepository;



    @PostConstruct
    public void init() throws SQLException {
        List<Partner> list = partnerRepository.findAll();
        list.forEach(p ->
                redisInstance.set("partnerKey_" + p.getShortname(), p.getPublicKey())
        );
    }

    public boolean checkPartner(String partner) {
        return redisInstance.has("partnerKey_" + partner);
    }

    public String getKeyPartner(String partner) {
        return redisInstance.get("partnerKey_" + partner);
    }


    public void setKeyUpload(String key) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, 15);
        redisInstance.set("UploadKey_" + key, ParseUtil.toString(cal.getTime(), "yyyy/MM/dd HH:mm"));
    }

    public boolean checkUploadKey(String key) {
        String date = redisInstance.get("UploadKey_" + key);
        if (date == null) return false;
        try {
            Date date1 = ParseUtil.stringToDate(date, "yyyy/MM/dd HH:mm");
            if (date1.compareTo(new Date()) < 0) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            return false;
        }
    }

}
