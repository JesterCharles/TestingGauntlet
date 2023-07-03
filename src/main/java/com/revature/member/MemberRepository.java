package com.revature.member;

import com.revature.utility.ConnectionFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.IOException;
import java.util.List;

public class MemberRepository {

    public Member create(Member newMember) {
        try {
            Session session = ConnectionFactory.getSession();
            Transaction transaction = session.beginTransaction();
            session.save(newMember);
            transaction.commit();
            return newMember;
        } catch (HibernateException | IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            ConnectionFactory.closeSession();
        }
    }

    public List<Member> findAll() {
        try {
            Session session = ConnectionFactory.getSession();
            Transaction transaction = session.beginTransaction();
            List<Member> members = session.createQuery("FROM Member").list();
            transaction.commit();
            return members;
        } catch (HibernateException | IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            ConnectionFactory.closeSession();
        }
    }

    public Member findById(String id) {
        try {
            Session session = ConnectionFactory.getSession();
            Transaction transaction = session.beginTransaction();
            Member member = session.get(Member.class, id);
            transaction.commit();
            return member;
        } catch (HibernateException | IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            ConnectionFactory.closeSession();
        }
    }

    public boolean update(Member updatedMember) {
        try {
            Session session = ConnectionFactory.getSession();
            Transaction transaction = session.beginTransaction();
            session.merge(updatedMember);
            transaction.commit();
            return true;
        } catch (HibernateException | IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            ConnectionFactory.closeSession();
        }
    }

    public boolean delete(String id) {
        try {
            Session session = ConnectionFactory.getSession();
            Transaction transaction = session.beginTransaction();
            Member member = session.load(Member.class, id);
            session.remove(member);
            transaction.commit();
            return true;
        } catch (HibernateException | IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            ConnectionFactory.closeSession();
        }
    }

    public Member loginCredentialCheck(String email, String password) {

        try {
            Session session = ConnectionFactory.getSession();
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("from Member where email= :email and password= :password");
            query.setParameter("email", email);
            query.setParameter("password", password);
            Member member = (Member) query.uniqueResult();
            transaction.commit();
            return member;
        } catch (HibernateException | IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            ConnectionFactory.closeSession();
        }
    }

    public boolean checkEmail(String email) {
        try {
            Session session = ConnectionFactory.getSession();
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("from Member where email= :email");
            query.setParameter("email", email);
            Member member = (Member) query.uniqueResult();
            transaction.commit();
            if(member == null) return true;
            return false;
        } catch (HibernateException | IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            ConnectionFactory.closeSession();
        }
    }

}
