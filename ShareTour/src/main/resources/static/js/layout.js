jQuery(document).ready(function(){

    $('.main_menu > li').mouseover(function(){
        $(this).find('.sub_menu').stop().slideDown(500);
    })

    $('.main_menu > li').mouseout(function(){
        $(this).find('.sub_menu').stop().slideUp(500);
    })

    setInterval(function(){

        $('.slide').delay(3000)
        $('.slide').animate({"left":"-1200px"})

        $('.slide').delay(3000)
        $('.slide').animate({"left":"-2400px"})

        $('.slide').delay(3000)
        $('.slide').animate({"left":"0px"})
    })

    function updateTimer() {
        const future = Date.parse("2023/09/27 19:00:00");
        const now = new Date();
        const diff = future - now;

        const days = Math.floor(diff / (1000 * 60 * 60 * 24));
        const hours = Math.floor(diff / (1000 * 60 * 60));
        const mins = Math.floor(diff / (1000 * 60));
        const secs = Math.floor(diff / 1000);

        const d = days;
        const h = hours - days * 24;
        const m = mins - hours * 60;
        const s = secs - mins * 60;

        document.getElementById("timer")
         .innerHTML =
         '<div>' + d + '<span>일</span></div>' +
         '<div>' + h + '<span>시</span></div>' +
         '<div>' + m + '<span>분</span></div>' +
         '<div>' + s + '<span>초</span></div>';
    }

    setInterval(updateTimer, 1000);
});