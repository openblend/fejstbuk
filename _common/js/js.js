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