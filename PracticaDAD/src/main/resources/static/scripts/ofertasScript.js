/**
 * 
 */
$(document).ready(function() {
    $('#list').click(function(event){event.preventDefault();$('#products').addClass('list');$('#products .item').removeClass('grid');});
    $('#grid').click(function(event){event.preventDefault();$('#products').removeClass('list');$('#products .item').addClass('grid');});
});

function grid() {
	document.getElementById("item").classList.add("itemGrid");
	document.getElementById("item").classList.remove("itemList");
}
function list() {
	document.getElementById("item").classList.add("itemList");
	document.getElementById("item").classList.remove("itemGrid");
}