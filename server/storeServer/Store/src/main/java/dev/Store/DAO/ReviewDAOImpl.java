//package dev.Store.DAO;
//
//import dev.Store.Repository.ReviewRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Optional;
//
//@Repository
//public class ReviewDAOImpl implements ReviewDAO {
//    private final ReviewRepository reviewRepository;
//
//    public ReviewDAOImpl(@Autowired ReviewRepository reviewRepository) {
//        this.reviewRepository = reviewRepository;
//    }
//
//    @Override
//    public String create(ReviewEntity reviewEntity) {
//        Long reviewNumber = reviewEntity.getReviewNumber();
//        String valid = globalValidCheck.validCheck(reviewEntity);
//
//        if (valid.equals("success")) {
//            reviewEntity.setReviewDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
//            reviewRepository.save(reviewEntity);
//
//            SalesPostEntity salesPostEntity = reviewEntity.getSalesPostEntity();
//
//            if (salesPostEntity != null) {
//                salesPostEntity.addReview(reviewEntity);
//            }
//        }
//        return valid;
//    }
//
//    @Override
//    public Map<String, Object> readAllByWriter(String reviewWriter) {
//        List<ReviewEntity> reviewEntityList = reviewRepository.findAllByReviewWriter(reviewWriter);
//        Map<String, Object> result = new HashMap<>();
//        if(reviewEntityList.isEmpty()){
//            result.put("result", "Error: No reviews found fot writer");
//        } else{
//            result.put("data", reviewEntityList);
//            result.put("result", "success");
//        }
//        return result;
//    }
//
//    @Override
//    public Map<String, Object> readAll() {
//        List<ReviewEntity> reviewEntityList = reviewRepository.findAll();
//        Map<String, Object> result = new HashMap<>();
//        if(reviewEntityList.isEmpty()){
//            result.put("result", "Error: No reviews found");
//        } else{
//            result.put("data", reviewEntityList);
//            result.put("result", "success");
//        }
//        return result;
//    }
//
//    @Override
//    public String update(ReviewEntity reviewEntity) {
//        if (reviewRepository.existsByReviewNumber(reviewEntity.getReviewNumber())) {
//            ReviewEntity oldReviewEntity = reviewRepository.findByReviewNumber(reviewEntity.getReviewNumber());
//            reviewEntity.setReviewWriter(Optional.ofNullable(reviewEntity.getReviewWriter()).orElse(oldReviewEntity.getReviewWriter()));
//            reviewEntity.setReviewContents(Optional.ofNullable(reviewEntity.getReviewContents()).orElse(oldReviewEntity.getReviewContents()));
//            reviewEntity.setReviewDate(Optional.ofNullable(reviewEntity.getReviewDate()).orElse(oldReviewEntity.getReviewDate()));
//            reviewEntity.setReviewLike(Optional.ofNullable(reviewEntity.getReviewLike()).orElse(oldReviewEntity.getReviewLike()));
//            reviewEntity.setReviewStarRating(Optional.ofNullable(reviewEntity.getReviewStarRating()).orElse(oldReviewEntity.getReviewStarRating()));
//            reviewEntity.setSalesPostEntity(Optional.ofNullable(reviewEntity.getSalesPostEntity()).orElse(oldReviewEntity.getSalesPostEntity()));
//
//            // 연관된 SalesPostEntity와의 연관 관계 설정
//            SalesPostEntity salesPostEntity = reviewEntity.getSalesPostEntity();
//            if (salesPostEntity != null) {
//                salesPostEntity.addReview(reviewEntity);
//            }
//
//            reviewRepository.save(reviewEntity);
//            return "success";
//        } else{
//            return "Error: Review not found";
//        }
//    }
//
//    @Override
//    public String delete(Long reviewNumber) {
//        if (reviewRepository.existsByReviewNumber(reviewNumber)) {
//            ReviewEntity reviewEntity = reviewRepository.findByReviewNumber(reviewNumber);
//
//            // 연관된 SalesPostEntity의 inquiries 컬렉션에서 삭제
//            SalesPostEntity salesPostEntity = reviewEntity.getSalesPostEntity();
//            if (salesPostEntity != null) {
//                salesPostEntity.removeReview(reviewEntity);
//            }
//
//            reviewRepository.deleteById(reviewNumber);
//            return "success";
//        } else {
//            return "Error: Inquiry not found";
//        }
//    }
//}