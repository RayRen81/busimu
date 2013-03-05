/**
 * @author elihuwu
 */
$(function () {
	
	$("#select-way").change(function(){
		var speed = 'normal';
		if(this.options[this.selectedIndex].value == 'endDate') {
			$("#endDateShedule").slideDown(speed);
			$("#lastTimeSchedule").slideUp(speed);
			
		} else {
			$("#endDateShedule").slideUp(speed);
			$("#lastTimeSchedule").slideDown(speed);
		}
//		$("#endDateShedule").toggleClass('hidden')
//		$("#lastTimeSchedule").toggleClass('hidden');
	});
	
	$(".dateInput").datepicker({showWeek: true, firstDay: 1, dateFormat: "yy-mm-dd"});
	$.datepicker.setDefaults($.datepicker.regional['zh-CN']);
	
});
