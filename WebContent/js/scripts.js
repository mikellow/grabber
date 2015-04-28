//template control

console.log("hello scripts.js")


$(document).ready(function(){

	$('.row.category').each(function(){
		activateControllButtons($(this))
		

	})
})


function activateControllButtons(container){

		activateShowMoreButton(container);
		activateHideCategoryButton(container);
}

function activateShowMoreButton(container){
	var showMoreButton = $(container).find('a.show-more');//.first();
	showMoreButton.bind({
		click: function(e) {
			e.preventDefault()
			console.log("clicked");
			var firstHiddenRow = $(container).find('.row.hidden').first();
			firstHiddenRow.removeClass("hidden");
		}
	})
}

function activateHideCategoryButton(container){
	var hideCategoryButton = $(container).find('a.hide-category');//.first();
	hideCategoryButton.bind({
		click: function(e) {
			e.preventDefault()
			container.fadeOut(1000)
		}
	})
}