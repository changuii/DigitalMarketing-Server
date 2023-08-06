//package com.example.sales_post.Service;
//
//import com.example.sales_post.DAO.DAO;
//import com.example.sales_post.Entity.SalesPostEntity;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class SalesPostServiceImpl implements SalesPostService{
//
//    private final DAO dao;
//    public SalesPostServiceImpl(@Autowired DAO dao){
//        this.dao = dao;
//    }
//
//
//    public SalesPostEntity createPost(DAO dao){
//        SalesPostEntity salesPost = new SalesPostEntity();
//        salesPost.setPostNumber(dao.getpostNumber();)
//        salesPost.setcategory(dao.getcategory());
//        salesPost.setpostTitle(dao.getpostTitle());
//        salesPost.setpostAuthor(dao.getpostAuthor());
//        salesPost.setpostDate(dao.getpostDate());
//        salesPost.setpostContents(dao.getpostContents());
//        salesPost.setpostPicture(dao.getpostPicture());
//        salesPost.setpostHitCount(dao.getpostHitCount());
//        salesPost.setpostLike(dao.getpostLike());
//        salesPost.setstoreLocation(dao.getstoreLocation());
//        );
//        return salesPost;
//    }
//
//
//    public SalesPostEntity readPost(long postNumber){
//        return this.dao.readPost(postNumber); // dao -> 수정
//    }
//    public void updatePost(DAO dao){
//        SalesPostEntity salesPost = this.dao.readPost(dao.getpostNumber());
//        salesPost.setPostNumber(dao.getpostNumber());
//        salesPost.setcategory(dao.getcategory());
//        salesPost.setpostTitle(dao.getpostTitle());
//        salesPost.setpostAuthor(dao.getpostAuthor());
//        salesPost.setpostDate(dao.getpostDate());
//        salesPost.setpostContents(dao.getpostContents());
//        salesPost.setpostPicture(dao.getpostPicture());
//        salesPost.setpostHitCount(dao.getpostHitCount());
//        salesPost.setpostLike(dao.getpostLike());
//        salesPost.setstoreLocation(dao.getstoreLocation());
//        this.dao.updatePost(salesPost);
//    }
//
//    public void deletePost(long postNumber){
//        this.dao.deletePost(postNumber);
//    }
//}
