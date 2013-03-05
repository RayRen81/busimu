<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="tabs">
	<ul>
		<li><a href="#tabs-1"><s:text name="decision.process.tab1.name"/></a></li>
		<li><a href="#tabs-2"><s:text name="decision.process.tab2.name"/></a></li>
		<li><a href="#tabs-3"><s:text name="decision.process.tab3.name"/></a></li>
	</ul>
	<div id="tabs-1">
		<%@ include file="/fragment/player/decision_round_tab1.inc" %>
	</div>
	<div id="tabs-2">
		<%@ include file="/fragment/player/decision_round_tab2.inc" %>
	</div>
	<div id="tabs-3">
		<%@ include file="/fragment/player/decision_round_tab3.inc" %>
	</div>
</div>