
jQuery(document).ready( function() {
	
	// Check for click events on the navbar burger icon
	$(".navbar-burger").click(function() {
	
		// Toggle the "is-active" class on both the "navbar-burger" and the "navbar-menu"
		$(".navbar-burger").toggleClass("is-active");
		$(".navbar-menu").toggleClass("is-active");
	});
	  
	// make iframes full height
	$('.oapen-snippet iframe').on("load", function() {

		var iframe = $(window.top.document).find(".oapen-snippet iframe");
		iframe.height(iframe[0].ownerDocument.body.scrollHeight+'px' );
	});	  
	  
	$(".ajaxloader").each( function() {
		
		var src = $(this).attr("data-src");
		$(this).html(" <i class='fa fa-spinner fa-pulse fa-fw'></i><i>&nbsp;connecting to library...</i>" );
		$(this).load( src, function() {
			// whatever
		});				
	});	  

	$(".carouselloader").each( function() {
		
		var src = $(this).attr("data-src");
		$(this).html(" <i class='fa fa-spinner fa-pulse fa-fw'></i><i>&nbsp;connecting to library...</i>" );
		$(this).load( src, function() {
			bulmaCarousel.attach('#slider', {
				slidesToScroll: 1,
				slidesToShow: 6,
			});
		});				
	});	  
	
	
});


