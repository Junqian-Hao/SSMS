function animate(lh,speed,delay,el,length){
	var timer = "";
	var index = 1;
	var el = document.getElementById(el);
	function start(){
		timer = setInterval(scrolling,speed);
	}
	function scrolling(){
		if(el.scrollTop < lh*index){
			el.scrollTop += 1;
		}else{
			clearInterval(timer);
			setTimeout(function(){
				index++;
				timer = setInterval(scrolling,speed);
				if(index == length+1){
					clearInterval(timer);
					el.scrollTop = 0;
					index = 1;
					timer = setInterval(scrolling,speed);
				}
			},500);
		}
	}
	setTimeout(start,delay);
}
