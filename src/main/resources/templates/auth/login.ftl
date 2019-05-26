

<#include "/inc/layout.ftl">

<@layout title="登录">



    <div class="row">

        <div class="col-md-4 col-md-offset-4">

            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">请登录</h3>
                </div>

                <div class="panel-body">

                    <form method="POST" action="login" accept-charset="UTF-8">
                        <div class="form-group">
                            <label class="control-label" for="username">账号</label>
                            <input class="form-control" name="username" type="text" value="asule" required>
                        </div>
                        <div class="form-group">
                            <label class="control-label" for="password">密码</label>
                            <input class="form-control" name="password" type="password" value="qwerty" required>
                        </div>
                        <div class="form-group">
                            <label>
                                <input type="checkbox" name="rememberMe" value="1"> 记住登录？
                            </label>
                            <span class="pull-right">
                                <a class="forget-password" href="${base}/forgot">忘记密码？</a>
                            </span>
                        </div>
                        <div class="form-group">
                            <button type="submit" class="btn btn-success btn-block">
                                登录 Use it
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

</@layout>


