<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="/common/tld/extremecomponents.tld" prefix="ec"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/common/Scripts/jquery-1.7.2.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/common/Scripts/jquery-ui-1.8.22.custom.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/common/Styles/ui-lightness/jquery-ui-1.8.22.custom.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/common/Styles/extremecomponents.css" />	
<STYLE type=text/css>

html {
	font-size: 100%;
	-webkit-text-size-adjust: 100%;
	-ms-text-size-adjust: 100%
}
body {
	margin: 0;
	font-family: "Microsoft Yahei", "Helvetica Neue", Helvetica, Arial,
		sans-serif;
	font-size: 13px;
	line-height: 18px;
	color: #333;
	background-color: #fff
}

.alert {
	padding: 8px 35px 8px 14px;
	margin-bottom: 18px;
	color: #c09853;
	text-shadow: 0 1px 0 rgba(255, 255, 255, 0.5);
	background-color: #fcf8e3;
	border: 1px solid #fbeed5;
	-webkit-border-radius: 4px;
	-moz-border-radius: 4px;
	border-radius: 4px
}

.alert-info {
	color: #3a87ad;
	background-color: #d9edf7;
	border-color: #bce8f1
}


.alert .tip {
    height: 0;
    margin: 0 8px;
    border: 4px solid transparent;
    border-left-color: #000;
    display: inline-block;
}




a:hover {
	color: #005580;
	text-decoration: underline
}
a:focus {
	outline: thin dotted #333;
	outline: 5px auto -webkit-focus-ring-color;
	outline-offset: -2px
}
a {
	color: #3a87ad;
	text-decoration: none
}

</STYLE>