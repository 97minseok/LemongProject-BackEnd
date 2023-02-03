package site.lemongproject.web.template.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import site.lemongproject.common.response.ResponseBody;
import site.lemongproject.common.response.ResponseBuilder;
import site.lemongproject.web.member.model.vo.Member;
import site.lemongproject.web.member.model.vo.Profile;
import site.lemongproject.web.template.model.dto.Review;
import site.lemongproject.web.template.model.dto.Template;
import site.lemongproject.web.template.model.dto.TemplateTodo;
import site.lemongproject.web.template.model.vo.*;
import site.lemongproject.web.template.service.TemplateReadService;
import site.lemongproject.web.template.service.TemplateWriteService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/template")
@RequiredArgsConstructor
public class TemplateController {
    final private TemplateWriteService WriteService;
    final private TemplateReadService ReadService;

    @GetMapping(value = {"/list", "/list/{page}", "/list/{page}/{categoryNo}"})
    public ResponseBody<List<Template>> list(@PathVariable(value = "page", required = false) Optional<Integer> p,
                                             @PathVariable(value = "categoryNo", required = false) Optional<Integer> cno) {
        int categoryNo = cno.orElse(0);//없는 경우 모든 카테고리
        int page = p.orElse(0);//없는 경우 0페이지
        List<Template> templateList = ReadService.getTemplateList(categoryNo, page);
        if (templateList != null && templateList.size() > 0) {
            return ResponseBuilder.success(templateList);
        } else {
            return ResponseBuilder.findNothing();
        }
    }

    @GetMapping(value = {"/one/{templateNo}"})
    public ResponseBody<List<Template>> list(@PathVariable(value = "templateNo") int templateNo) {
        Template template = ReadService.getTemplateDetail(templateNo);
        if (template != null) {
            return ResponseBuilder.success(template);
        } else {
            return ResponseBuilder.findNothing();
        }
    }


    @GetMapping("/unsave/load")
    public ResponseBody<Template> load(@SessionAttribute("loginUser") Profile loginMember) {
        Template cur = WriteService.loadInsertPage(loginMember.getUserNo());
        ResponseBody<Template> responseBody = new ResponseBody<>();
        responseBody.setCode("2000");
        responseBody.setMessage("SUCCESS");
        ResponseBody<Template> response = ResponseBuilder.success(cur);
        return response;
    }
    @GetMapping("/todo/detail/{templateNo}")
    public ResponseBody<List<TemplateTodo>> todoDetail(int templateNo) {
        List<TemplateTodo> todoList = ReadService.getTemplateTodo(templateNo);
        if(todoList!=null&&todoList.size()>0){
            return ResponseBuilder.success(todoList);
        }else{
            return ResponseBuilder.findNothing();
        }
    }
    @PutMapping(value = "/unsave/upload")
    public ResponseBody<Integer> upload(@SessionAttribute("loginUser") Profile loginUser) {
        int result = WriteService.uploadUnSave(loginUser.getUserNo());
        if (result > 0) {
            return ResponseBuilder.success(result);
        } else {
            return ResponseBuilder.upLoadFail();
        }
    }

    @PutMapping("/unsave/update")
    public ResponseBody<Integer> updateUnSave(@SessionAttribute("loginUser") Profile loginUser, @RequestBody TemplateUpdateVo tuv) {
        if ((tuv == null || tuv.getTemplateNo() == null) ||
                (tuv.getRange() == null && tuv.getTitle() == null && tuv.getCategoryNo() == null && tuv.getContent() == null)) {
            return ResponseBuilder.upLoadFail();
        }
        tuv.setUserNo(loginUser.getUserNo());
        int result = WriteService.updateUnSaveTemplate(tuv);
        if (result > 0) {
            return ResponseBuilder.success(1);
        } else {
            return ResponseBuilder.upLoadFail();
        }
    }

    @PutMapping("/unsave/reset")
    public ResponseBody<Template> resetUnSave(@SessionAttribute("loginUser") Profile loginUser) {

        Template t = WriteService.resetUnSave(loginUser.getUserNo());
        if (t != null) {
            return ResponseBuilder.success(t);
        } else {
            return ResponseBuilder.upLoadFail();
        }
    }


    @PostMapping("/todo/insert")
    public ResponseBody<List<TemplateTodo>> insertTodo(@SessionAttribute("loginUser")Profile loginUser, @RequestBody TempalteTodoInsertVo tiv) {
        tiv.setUserNo(loginUser.getUserNo());
        int result = WriteService.insertTodo(tiv);
        if (result>0) {
            return ResponseBuilder.success(result);
        } else {
            return ResponseBuilder.upLoadFail();
        }
    }

    @DeleteMapping("/todo/deleteUnSave/{tpTodoNo}")
    public ResponseBody<Integer> deleteTodo(@SessionAttribute("loginUser") Profile loginUser, @PathVariable("tpTodoNo") int tpTodoNo) {
        int result = WriteService.deleteTodo(new TPTodoDeleteVo(1, tpTodoNo));
        if (result > 0) {
            return ResponseBuilder.success(result);
        } else {
            return ResponseBuilder.deleteFail();
        }
    }
    @DeleteMapping("/delete/{templateNo}")
    public ResponseBody<Integer> deleteTemplate(@SessionAttribute("loginUser") Profile loginUser, @PathVariable("templateNo") int templateNo) {
        int result = ReadService.deleteTemplate(loginUser.getUserNo(),templateNo);
        if (result > 0) {
            return ResponseBuilder.success(result);
        } else {
            return ResponseBuilder.deleteFail();
        }
    }
    @PostMapping("/review/insert")
    public ResponseBody<Integer> reviewInsert(@SessionAttribute("loginUser")Profile loginUser, ReviewInsertVo riv){
        riv.setUserNo(loginUser.getUserNo());
        int result=ReadService.insertReview(riv);
        if(result>0){
            return ResponseBuilder.success(result);
        }else{
            return ResponseBuilder.upLoadFail();
        }
    };
    @DeleteMapping("/review/delete/{reviewNo}")
    public ResponseBody<Integer> reviewDelete(@SessionAttribute("loginUser")Member loginUser, @PathVariable("reviewNo")int reviewNo){
        ReviewDeleteVo reviewDeleteVo=new ReviewDeleteVo(reviewNo,loginUser.getUserNo());
        int result=ReadService.deleteReview(reviewDeleteVo);
        if(result>0){
            return ResponseBuilder.success(result);
        }else{
            return ResponseBuilder.upLoadFail();
        }
    };
    @GetMapping("/review/list/{templateNo}")
    public ResponseBody<List<Review>> reviewList(@PathVariable("templateNo")int templateNo){
        List<Review> rList=ReadService.getReviewList(templateNo);
        if(rList!=null){
            return ResponseBuilder.success(rList);
        }else{
            return ResponseBuilder.findNothing();
        }
    }
    @GetMapping("/review/one/{reviewNo}")
    public ResponseBody<Review> reviewOne(@PathVariable("reviewNo")int reviewNo){
        Review review=ReadService.getReviewOne(reviewNo);
        if(review!=null){
            return ResponseBuilder.success(review);
        }else{
            return ResponseBuilder.findNothing();
        }
    }


}
