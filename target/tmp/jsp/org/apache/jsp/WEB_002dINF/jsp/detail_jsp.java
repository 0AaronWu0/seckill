/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: jetty/9.2.2.v20140723
 * Generated at: 2017-04-16 01:29:02 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class detail_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(1);
    _jspx_dependants.put("/WEB-INF/jsp/common/head.jsp", Long.valueOf(1480828624229L));
  }

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html>\r\n");
      out.write("   <head>\r\n");
      out.write("      <title>秒杀详情页</title>\r\n");
      out.write("\t\t");
      out.write("      <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n");
      out.write("      <!-- å¼å¥ Bootstrap -->\r\n");
      out.write("      <link href=\"http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css\" rel=\"stylesheet\">\r\n");
      out.write(" \r\n");
      out.write("      <!-- HTML5 Shim å Respond.js ç¨äºè®© IE8 æ¯æ HTML5åç´ ååªä½æ¥è¯¢ -->\r\n");
      out.write("      <!-- æ³¨æï¼ å¦æéè¿ file://  å¼å¥ Respond.js æä»¶ï¼åè¯¥æä»¶æ æ³èµ·ææ -->\r\n");
      out.write("      <!--[if lt IE 9]>\r\n");
      out.write("         <script src=\"https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js\"></script>\r\n");
      out.write("         <script src=\"https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js\"></script>\r\n");
      out.write("      <![endif] -->");
      out.write("\r\n");
      out.write("   </head>\r\n");
      out.write("   <body>\r\n");
      out.write(" \t\t<input type=\"hidden\" id=\"basePath\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${basePath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("\" />\r\n");
      out.write("\t<div class=\"container\">\r\n");
      out.write("\t\t<div class=\"panel panel-default text-center\">\r\n");
      out.write("\t\t\t<div class=\"panel-heading\">\r\n");
      out.write("\t\t\t\t<h1>");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${seckill.name}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("</h1>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<div class=\"panel-body\">\r\n");
      out.write("\t\t\t\t<h2 class=\"text-danger\">\r\n");
      out.write("\t\t\t\t\t<!-- 显示time图标 -->\r\n");
      out.write("\t\t\t\t\t<span class=\"glyphicon glyphicon-time\"></span>\r\n");
      out.write("\t\t\t\t\t<!-- 展示倒计时 -->\r\n");
      out.write("\t\t\t\t\t<span class=\"glyphicon\" id=\"seckillBox\"></span>\r\n");
      out.write("\t\t\t\t</h2>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("\r\n");
      out.write("\t<!-- 登录弹出层，输入电话 -->\r\n");
      out.write("\t<div id=\"killPhoneModal\" class=\"modal fade\">\r\n");
      out.write("\t\t<div class=\"modal-dialog\">\r\n");
      out.write("\t\t\t<div class=\"modal-content\">\r\n");
      out.write("\t\t\t\t<div class=\"modal-header\">\r\n");
      out.write("\t\t\t\t\t<h3 class=\"modal-title text-center\">\r\n");
      out.write("\t\t\t\t\t\t<span class=\"glyphicon glyphicon-phone\"></span>秒杀电话：\r\n");
      out.write("\t\t\t\t\t</h3>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div class=\"modal-body\">\r\n");
      out.write("\t\t\t\t\t<div class=\"row\">\r\n");
      out.write("\t\t\t\t\t\t<div class=\"col-xs-8 col-xs-offset-2\">\r\n");
      out.write("\t\t\t\t\t\t\t<input type=\"text\" name=\"killphone\" id=\"killphoneKey\"\r\n");
      out.write("\t\t\t\t\t\t\t\tplaceholder=\"填手机号^O^\" class=\"form-control\" />\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div class=\"modal-footer\">\r\n");
      out.write("\t\t\t\t\t<span id=\"killphoneMessage\" class=\"glyphicon\"></span>\r\n");
      out.write("\t\t\t\t\t<button type=\"button\" id=\"killPhoneBtn\" class=\"btn btn-success\">\r\n");
      out.write("\t\t\t\t\t\t<span class=\"glyphicon glyphicon-phone\"></span> Submit\r\n");
      out.write("\t\t\t\t\t</button>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("\r\n");
      out.write("\t<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->\r\n");
      out.write("\t\t<script src=\"http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js\"></script>\r\n");
      out.write("\t\t<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->\r\n");
      out.write("\t\t<script src=\"http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js\"></script>\r\n");
      out.write("\t<!-- jQuery cookie操作插件 -->\r\n");
      out.write("\t<script src=\"//cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.min.js\"></script>\r\n");
      out.write("\t<!-- jQery countDonw倒计时插件  -->\r\n");
      out.write("\t<script src=\"//cdn.bootcss.com/jquery.countdown/2.1.0/jquery.countdown.min.js\"></script>\r\n");
      out.write("\t<!-- 开始编写交互逻辑 -->\r\n");
      out.write("\t<script src=\"/resources/js/seckill.js\"  type=\"text/javascript\"></script>\r\n");
      out.write("\t<script type=\"text/javascript\" >\r\n");
      out.write("\t\t$(function(){\r\n");
      out.write("\t\t\t//使用EL表达式传入参数\r\n");
      out.write("\t\t\tseckill.detail.init({\r\n");
      out.write("\t\t\t\tseckillId : ");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${seckill.seckillId}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write(",\r\n");
      out.write("\t\t\t\tstartTime : ");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${seckill.startTime.time}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write(",//毫秒\r\n");
      out.write("\t\t\t\tendTime : ");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${seckill.endTime.time}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t});\r\n");
      out.write("\t</script>\r\n");
      out.write("   </body>\r\n");
      out.write("   \t\t   \r\n");
      out.write("</html>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
