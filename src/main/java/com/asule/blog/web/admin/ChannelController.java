package com.asule.blog.web.admin;

import com.asule.blog.base.lang.Consts;
import com.asule.blog.base.lang.Result;
import com.asule.blog.modules.po.Channel;
import com.asule.blog.modules.service.ChannelService;
import com.asule.blog.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller("adminChannelController")
@RequestMapping("/admin/channel")
public class ChannelController extends BaseController{


    @Autowired
    private ChannelService channelService;

    @RequestMapping("/list")
//	@RequiresPermissions("channel:list")
    public String list(ModelMap model) {
        model.put("channels", channelService.findAll(Consts.IGNORE_CHANNEL_STATUS));
        return "/admin/channel/list";
    }


    @GetMapping("/weight")
    @ResponseBody
    public Result updateWeight(Integer channelId) {
        channelService.setMaxWeight(channelId);
        return Result.success();
    }


    @ResponseBody
    @RequestMapping("/delete")
    public Result delete(@RequestParam(value = "id") Integer channelId) {
        channelService.deleteChannel(channelId);
        return Result.success();
    }

    @GetMapping("/view")
    public String view(@RequestParam(value = "id",required = false) Integer channelId,ModelMap model) {
        if (channelId!=null){
            Channel channel=channelService.findById(Consts.IGNORE_CHANNEL_STATUS, channelId);
            model.put("view",channel);
        }
        return "/admin/channel/view";
    }


    @PostMapping("/update")
    public String update(Channel view) {
        if (view != null) {
            channelService.update(view);
        }
        return "redirect:/admin/channel/list";
    }
}
