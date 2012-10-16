/* onload settings */
$().ready(function () {
	$('body').removeClass('nojs');
	
	/* lightbox */
	$('a.lightbox').fancybox({
		'transitionIn'		: 'elastic',
		'transitionOut'		: 'elastic',
		'width'				: 740,
		'height'			: 380,
		'speedIn'			: 600,
		'speedOut'			: 200,
		'overlayShow'		: true,
		'overlayOpacity'	: 0.0
	});
	$('a.ajax').fancybox({
		'autoDimensions'	: true,
		'padding'			: 0,
		'overlayShow'		: true,
		'overlayOpacity'	: 0.0,
		ajax 				: {type : 'POST'}
	});
});
REST_API_URL = "/rest/post";

function addComment(el,postId){
    var url = REST_API_URL + "/add-comment?";
    url += "postId="+postId+"&comment="+el.value;
    $.ajax({
      url: url,
      type : 'POST',
        success: function(data) {
            $('.result').html(data);
            alert('Load was performed.');
          }
    });
    return false;
}