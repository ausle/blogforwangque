package com.asule.blog.web.site;


import com.asule.blog.base.lang.Consts;
import com.asule.blog.config.SiteOptions;
import com.asule.blog.modules.po.Channel;
import com.asule.blog.modules.service.ChannelService;
import com.asule.blog.web.BaseController;
import com.asule.blog.web.Views;
import com.sun.media.sound.MidiOutDeviceProvider;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class IndexController extends BaseController {


    @Autowired
    private ChannelService channelService;


    @GetMapping(value= {"/","/index"})
    public String root(ModelMap model, HttpServletRequest request) {

        String order = ServletRequestUtils.getStringParameter(request, "order", Consts.order.NEWEST);
        int pageNo = ServletRequestUtils.getIntParameter(request, "pageNo", 1);
        int channelId = ServletRequestUtils.getIntParameter(request, "channelId", 0);//默认获取所有类别文章
        long tagId = ServletRequestUtils.getLongParameter(request, "tagId", 0);

        model.put("channelId",channelId);
        model.put("tagId",tagId);
        model.put("order",order);
        model.put("pageNo",pageNo);

        String requestURI = request.getRequestURI();
        model.put("requestURI",requestURI);

        model.put("sortURI",requestURI+"?channelId="+channelId+"&tagId="+tagId);
        model.put("pageURI",requestURI+"?order="+order+"&channelId="+channelId+"&tagId="+tagId);

        return view(Views.INDEX);
    }
}

/*




*/

