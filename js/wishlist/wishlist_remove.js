
// 여기서 email 은 jsp 에서 만들어놓은 전역변수를 사용
$(document).ready(function() {
    $(document).on('click', '.wishlist_delete', function() {
        const winum = $(this).siblings('.delete_one').val();
        console.log("winum: " + winum);
        let csrfParameter = $('meta[name="_csrf_parameter"]').attr('content');
	    let csrfHeader = $('meta[name="_csrf_header"]').attr('content');
	    let csrfToken = $('meta[name="_csrf"]').attr('content');
        $.ajax({
            url: "/jejufriends/wishlist/removeOne.json",
            type: "POST",
            data: {winum: winum},
            beforeSend: function(xhr) {
	            xhr.setRequestHeader(csrfHeader, csrfToken);
	        },
            success: function() {
                let csrfParameter = $('meta[name="_csrf_parameter"]').attr('content');
                let csrfHeader = $('meta[name="_csrf_header"]').attr('content');
                let csrfToken = $('meta[name="_csrf"]').attr('content');
                $.ajax({
                    url: "/jejufriends/wishlist/wishlistList.json",
                    type: "POST",
                    data: {email:email},
                    beforeSend: function(xhr) {
                        xhr.setRequestHeader(csrfHeader, csrfToken);
                    },
                    success: function(wishlistList) {
                        let pageNum = $('#pageNum option:selected').val();
                        wishListPageSet(pageNum, 1, wishlistList);
                    }
                });
            }
        });
    });
    
    $(document).on('click', '#delete_all', function() {
        let csrfParameter = $('meta[name="_csrf_parameter"]').attr('content');
	    let csrfHeader = $('meta[name="_csrf_header"]').attr('content');
	    let csrfToken = $('meta[name="_csrf"]').attr('content');
        $.ajax({
            url: "/jejufriends/wishlist/removeAll.json",
            type: "POST",
            data: {email, email},
            beforeSend: function(xhr) {
	            xhr.setRequestHeader(csrfHeader, csrfToken);
	        },
            success: function() {
                let csrfParameter = $('meta[name="_csrf_parameter"]').attr('content');
                let csrfHeader = $('meta[name="_csrf_header"]').attr('content');
                let csrfToken = $('meta[name="_csrf"]').attr('content');
                $.ajax({
                    url: "/jejufriends/wishlist/wishlistList.json",
                    type: "POST",
                    data: {email:email},
                    beforeSend: function(xhr) {
                        xhr.setRequestHeader(csrfHeader, csrfToken);
                    },
                    success: function(wishlistList) {
                        let pageNum = $('#pageNum option:selected').val();
                        wishListPageSet(pageNum, 1, wishlistList);
                    }
                });
            }
        });
    });
});