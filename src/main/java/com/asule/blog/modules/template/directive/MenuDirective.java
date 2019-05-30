package com.asule.blog.modules.template.directive;

import com.asule.blog.base.utils.MenuJsonUtils;
import com.asule.blog.modules.po.Menu;
import com.asule.blog.modules.template.DirectiveHandler;
import com.asule.blog.modules.template.TemplateDirective;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MenuDirective extends TemplateDirective{

    @Override
    public String getName() {
        return "menus";
    }

    @Override
    protected void execute(DirectiveHandler directiveHandler) throws Exception {
        List<Menu> menus = filterMenu();
        directiveHandler.put(getName(), menus).render();
    }

    private List<Menu> filterMenu() {
        List<Menu> menus = MenuJsonUtils.getMenus();
//        if (!subject.hasRole(Role.ROLE_ADMIN)) {
//            menus = check(subject, menus);
//        }
        return menus;
    }
}
