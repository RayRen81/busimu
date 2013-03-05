/**
 * @author elihuwu
 */
$(function () {
	
	$("#tree-menu").jstree({ 
		"core" : {
			"animation" : 200,
			"initially_open" : "open"
		},
		"plugins" : [ "themes", "html_data" ]
	});
	
	$("#operation").change(function(){
		$("#corps").toggleClass("hidden");
	});
});
