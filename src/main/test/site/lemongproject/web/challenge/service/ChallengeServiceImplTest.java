package site.lemongproject.web.challenge.service;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import site.lemongproject.common.type.ChallengeStatus;
import site.lemongproject.common.type.ChallengeUserStatus;
import site.lemongproject.config.Configure;
import site.lemongproject.web.challenge.model.vo.ChallengeUserVo;
import site.lemongproject.web.challenge.model.vo.MultiCreateVo;
import site.lemongproject.web.challenge.model.vo.SingleStartVo;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

@Transactional
public class ChallengeServiceImplTest extends Configure {
    @Autowired ChallengeService challengeService;
    final int TEST_TP_NO=30;
    final int TEST_USER_NO=30;
    final int TEST_CL_NO=3000;




    @Test
    public void createMulti() {
        MultiCreateVo createVo=new MultiCreateVo();
        createVo.setUserNo(TEST_USER_NO);
        createVo.setTemplateNo(TEST_TP_NO);
        createVo.setOption("10000111");
        createVo.setChallengeTitle("제목입니다,");
        createVo.setChallengeInfo("내용입니다.,");
        createVo.setStartDate(LocalDate.now().plusDays(3));
        int result=challengeService.createMulti(createVo);

    }

    @Test
    public void startSingle() {
        SingleStartVo startVo=new SingleStartVo();
        startVo.setUserNo(TEST_USER_NO);
        startVo.setStartDate(LocalDate.now());
        startVo.setTemplateNo(TEST_TP_NO);
        startVo.setStatus(ChallengeStatus.READY);
        startVo.setOption("11011011");
        int result=challengeService.startSingle(startVo);
        assertThat(result).isNotZero();
    }
    @Test
    public void joinMulti(){
        ChallengeUserVo challengeUserVo=new ChallengeUserVo(TEST_USER_NO+1,TEST_CL_NO, ChallengeUserStatus.READY);
        int result=challengeService.joinMulti(challengeUserVo);
        assertThat(result).isNotZero();
    }
}