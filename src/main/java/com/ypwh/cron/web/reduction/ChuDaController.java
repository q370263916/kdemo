package com.ypwh.cron.web.reduction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ypwh.cron.chuda.service.ChuDaService;
import com.ypwh.cron.user.service.CronUserService;
import com.ypwh.framework.common.util.StringUtil;


/**
 * 拼团
 * @author zgz_j
 *
 */
@Controller
@RequestMapping("/task/chuda")
public class ChuDaController{
	
	private static final Logger logger = LoggerFactory.getLogger(ChuDaController.class);
	
	@Autowired
	CronUserService cronUserService;
	
	@Autowired
	ChuDaService chuDaService;
	
	@RequestMapping(value = "/init")
	@ResponseBody
	public Object initUser(HttpServletRequest request, HttpServletResponse response) {
		try{
			cronUserService.initUser();
			return "success";
		} catch (Exception e) {
			logger.error("updateBrowseNum error", e);
			return "error";
		}
	}
	@RequestMapping(value = "/del")
	@ResponseBody
	public Object del(HttpServletRequest request, HttpServletResponse response) {
		try{
			chuDaService.delRedis();
			return "success";
		} catch (Exception e) {
			logger.error("updateBrowseNum error", e);
			return "error";
		}
	}
	
	
	@RequestMapping(value = "/chuda")
	@ResponseBody
	public Object arrive(HttpServletRequest request, HttpServletResponse response) {
		try{
			int typeId=StringUtil.convertInt(request.getParameter("typeId"), 0);
			int sendType=StringUtil.convertInt(request.getParameter("sendType"), 0);
			chuDaService.run(typeId,sendType);
			System.out.println("==============触达1========================");

			return "success";
		} catch (Exception e) {
			logger.error("updateBrowseNum error", e);
			return "error";
		}
	}
	
	
	@RequestMapping(value = "/product")
	@ResponseBody
	public Object product(HttpServletRequest request, HttpServletResponse response) {
		try{
			int typeId=StringUtil.convertInt(request.getParameter("typeId"), 0);
			int sendType=StringUtil.convertInt(request.getParameter("sendType"), 0);
			chuDaService.productKafKa(typeId,sendType);



			return "success";
		} catch (Exception e) {
			logger.error("updateBrowseNum error", e);
			return "error";
		}
	}
	
	
	@RequestMapping(value = "/all")
	@ResponseBody
	public Object all(HttpServletRequest request, HttpServletResponse response) {
		try{
			
			chuDaService.delRedis();
			chuDaService.run(1,1);
			chuDaService.run(2,1);
			chuDaService.run(1,2);
			chuDaService.run(2,2);
			cronUserService.initUser();
			chuDaService.productKafKa(1,1);
			chuDaService.productKafKa(2,1);
			chuDaService.productKafKa(1,2);
			chuDaService.productKafKa(2,2);
			return "success";
		} catch (Exception e) {
			logger.error("updateBrowseNum error", e);
			return "error";
		}
	}
}
