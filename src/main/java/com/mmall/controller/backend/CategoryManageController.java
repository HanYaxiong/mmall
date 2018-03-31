package com.mmall.controller.backend;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Category;
import com.mmall.service.ICategoryService;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/manage/category")
public class CategoryManageController {

    @Autowired
    private ICategoryService iCategoryService;
    @Autowired
    private IUserService iUserService;

    /**
     * 添加类别
     * @param session
     * @param categoryName
     * @param parentId
     * @return
     */
    @RequestMapping(value = "add_category.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<Category> addCategory(HttpSession session, String categoryName,@RequestParam(value = "parentId",defaultValue = "0") int parentId) {
        return iCategoryService.addCategory(categoryName,parentId);
    }

    /**
     * 修改品类名称
     * @param session
     * @param categoryId
     * @param categoryName
     * @return
     */
    @RequestMapping("set_category_name.do")
    @ResponseBody
    public ServerResponse setCategoryName(HttpSession session,Integer categoryId,String categoryName){
        return iCategoryService.updateCategoryName(categoryId,categoryName);
    }

    /**
     * 获取品类子节点
     * @param session
     * @param categoryId
     * @return
     */
    @RequestMapping("get_category.do")
    @ResponseBody
    public ServerResponse getChildrenParallelCategory(HttpSession session,@RequestParam(value = "categoryId" ,defaultValue = "0") Integer categoryId){
        // 查询子节点的category信息,并且不递归,保持平级
        return iCategoryService.getChildrenParallelCategory(categoryId);
    }

    /**
     * 获取品类及后代品类节点
     * @param session
     * @param categoryId
     * @return
     */
    @RequestMapping("get_deep_category.do")
    @ResponseBody
    public ServerResponse getCategoryAndDeepChildrenCategory(HttpSession session,@RequestParam(value = "categoryId" ,defaultValue = "0") Integer categoryId){
        // 查询当前节点的id和递归子节点的id
        // 0->10000->100000
        return iCategoryService.selectCategoryAndChildrenById(categoryId);

    }


}
