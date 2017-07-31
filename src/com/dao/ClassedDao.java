package com.dao;

import com.EntityManagerTools;
import com.entites.Classes;

import javax.persistence.EntityManager;
import java.util.Date;

/**
 * @author wanli zhou
 * @created 2017-06-14 10:56 PM.
 */
public class ClassedDao {

    public void createClasses(){
        EntityManager em = null;
        try {
            em = EntityManagerTools.getEntityManager();
            System.out.println("querying all the managed entities...");

            Classes cl = new Classes();
            cl.setClassName("test-1");
            cl.setCreatedAt(new Date());
            cl.setUpdatedAt(new Date());
            cl.setVersion(1);
            em.getTransaction().begin();
            em.persist(cl);

            em.getTransaction().commit();
            em.createQuery("SELECT c FROM Classes c WHERE c.id = :id", Classes.class)
                    .setParameter("id", 1)
                    .getResultList().stream().forEach(c ->{
                System.out.println(c.getClassName());
            });
        } finally {
            if(em != null){
                em.close();
            }
        }
    }
}
