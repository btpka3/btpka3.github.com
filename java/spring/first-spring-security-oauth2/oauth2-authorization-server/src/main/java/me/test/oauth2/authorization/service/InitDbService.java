package me.test.oauth2.authorization.service;

import net.kingsilk.qh.oauth.domain.*;
import net.kingsilk.qh.oauth.repo.*;
import net.kingsilk.qh.oauth.security.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.event.*;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * 因为管理后台不完善，先再应用一启动的时候就导入一部分 "静态" 数据。
 */
@Service
public class InitDbService {

    public static final String user_id_superAdmin = "58de6b27785a82000005a140";
    public static final String user_id_httAdmin = "58e4bd0c785a82000005a141";
    public static final String user_id_zll = "58e5dfb42a9f2d0000313b1d";


    @Autowired
    private UserRepo userRepo;

    @Autowired
    private OAuthClientDetailsRepo oauthClientDetailsRepo;

    @Autowired
    private SecService secUtils;

    @EventListener
    public void onApplicationStated(ContextRefreshedEvent event) {

        checkAndAddSupperAdmin();
        checkAndAddHttAdmin();
        //checkAndAddUserZll();
    }

    /**
     * 超级管理员
     */
    private void checkAndAddSupperAdmin() {

        if (userRepo.findOne(user_id_superAdmin) != null) {
            return;
        }

        User user = new User();
        user.setId(user_id_superAdmin);
        user.setEnabled(true);
        user.setAccountLocked(false);
        user.setAccountExpired(false);
        user.setInviter(null);
        user.setUsername("qhAdmin");
        user.setPassword(secUtils.encodePassword("qhAdmin"));

        userRepo.save(user);
    }

    /**
     * 郝太太管理员
     */
    private void checkAndAddHttAdmin() {

        if (userRepo.findOne(user_id_httAdmin) != null) {
            return;
        }

        User user = new User();
        user.setId(user_id_httAdmin);
        user.setEnabled(true);
        user.setAccountLocked(false);
        user.setAccountExpired(false);
        user.setInviter(null);
        user.setUsername("httAdmin");
        user.setPassword(secUtils.encodePassword("httAdmin"));

        // 绑定手机号码
        user.setPhone("18069855776");
        user.setPhoneVerifiedAt(new Date());

        userRepo.save(user);
    }

    /**
     * 般若
     */
    private void checkAndAddUserZll() {

        if (userRepo.findOne(user_id_zll) != null) {
            return;
        }

        User user = new User();
        user.setId(user_id_zll);
        user.setEnabled(true);
        user.setAccountLocked(false);
        user.setAccountExpired(false);
        user.setInviter(null);

        // 绑定用户名密码
        user.setUsername("btpka3");
        user.setPassword(secUtils.encodePassword("btpka3"));

        // 绑定微信信息
        //user.setOpenId("oETxKwnXo4pPaK1S-AWyywQCCsJ4");


        // 绑定手机号码
        user.setPhone("17091602013");
        user.setPhoneVerifiedAt(new Date());

        userRepo.save(user);
    }


}
