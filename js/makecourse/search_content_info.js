
$(document).ready(function() {
    $(document).on("click", ".content_subject",function() {
        const contentname  = $(this).text();
        window.open(`/jejufriends/make_course/contentInfo.do?contentname=${contentname}`, contentname, 'width=' + screen.width + ',height=' + screen.height + ',fullscreen=yes', false);
    });
});

