package com.todoay.api.global.init;

import com.todoay.api.domain.auth.entity.Auth;
import com.todoay.api.domain.hashtag.entity.Hashtag;
import com.todoay.api.domain.profile.entity.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;

@RequiredArgsConstructor
@Configuration
public class InitialData {

    @PersistenceUnit
    EntityManagerFactory emf;
    private final BCryptPasswordEncoder encoder;


    @PostConstruct
    public void createInitialData() {

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Profile testProfile = Profile.builder()
                .nickname("테스터")
                .build();

        String encodedPwd = encoder.encode("qwer1234!");
        Auth testAuth = Auth.builder()
                .email("test@naver.com")
                .password(encodedPwd)
                .build();

        testAuth.associateWithProfile(testProfile);
        testAuth.completeEmailVerification();

        em.persist(testAuth);


        for (int i = 0; i < 10; i++) {
            Hashtag hashtag = new Hashtag("#태그" + i);
            em.persist(hashtag);
        }


        tx.commit();

    }
}
