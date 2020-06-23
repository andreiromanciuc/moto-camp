window.Timeline = {
    API_URL: "http://localhost:8083/timeline",

    getUserSession: function () {
        $.ajax({
            url: Timeline.API_URL + "/user/user",
            method: "GET"
        }).done(function (response) {
            // console.log(response);
            Timeline.getUserById(response);
            Timeline.getMotorById(response);
            Timeline.getPostsForUser(response);
        })
    },

    getUserById: function (id) {
        $.ajax({
            url: Timeline.API_URL + "/user/" + id,
            method: "GET"
        }).done(function (user) {
            // console.log(user);
            $(".profile-photo-timeline").html(Timeline.displayUserProfilePhoto(user));
            $(".profile-name-timeline").html(Timeline.displayUserName(user));
            $(".photo-form-timeline").html(Timeline.displayUsersProfilePhotoToFormGroup(user));

        });
    },

    displayUsersProfilePhotoToFormGroup: function (user) {
        return`<img src=${user.imageUrl} alt="" class="profile-photo-md"/>`
    },
    displayUserProfilePhoto: function (user) {
        return `<img src=${user.imageUrl}  alt="user" class="profile-photo"/>`
    },

    displayUserName: function (user) {
        return `<h5><a href="#" class="profile-name" id="username" data-id=${user.id}>${user.username}</a></h5>`
    },

    getMotorById: function (id) {
        $.ajax({
            url: Timeline.API_URL + "/motorcycle/" + id,
            method: "GET"
        }).done(function (motor) {
            $(".moto-photo-timeline").html(Timeline.displayMotorProfilePhoto(motor));
            $(".moto-name-timeline").html(Timeline.displayMotorUsername(motor));
        });
    },

    displayMotorProfilePhoto: function (motor) {
        return `<img src="${motor.imageUrl}" alt="moto_img" class="moto-photo">`
    },
    displayMotorUsername: function (motor) {
        return `<h5><a href="#" class="moto-name">${motor.userName}</a></h5>`
    },

    createPostFromTimeline: function () {
        $.ajax({
            url: Timeline.API_URL + "/user/user",
            method: "GET"
        }).done(function (response) {

            let requestBody = {
                title: $("#exampleTextarea1-timeline").val(),
                content: $("#exampleTextarea2-timeline").val(),
                imageUrl: "",
                userId: response
            };

            $.ajax({
                url: Timeline.API_URL + "/post",
                method: "POST",
                contentType: "application/json",
                data: JSON.stringify(requestBody)
            }).done(function (post) {
                // console.log(post);
                location.reload();
            });
        })

    },

    searchPostByTitle: function () {
        let search = $("#search").val();
        $.ajax({
            url: Timeline.API_URL + "/post/search?title=" + search,
            method: "GET"
        }).done(function (post) {
            $("#post-feed-timeline").html(Timeline.getHtmlForOnePostForUser(post));
        })
    },

    deletePost: function (id) {
        let result = confirm("Please confirm that you want to delete this post");
        if (result) {
        $.ajax({
            url: Timeline.API_URL + "/post/" + id,
            method: "DELETE"
        }).done(function () {
                location.reload();
            });
        }
        location.reload();
    },

    getPostById: function (id) {
        $.ajax({
            url: Timeline.API_URL + "/post/post/" + id,
            method: "GET"
        }).done(function (post) {
            // console.log(post);
            $("#post-feed-timeline").html(Timeline.getHtmlForOnePostForUser(post));
            $('#update-post').css("visibility", "visible");
            $('#save').css("visibility", "visible");
        })
    },


    updatePost: function (id) {

        let content = $('.update-message').val();

        let requestBody = {
            content: content,
            postId: id
        };

        $.ajax({
            url: Timeline.API_URL + "/post",
            method: "PUT",
            contentType: "application/json",
            data: JSON.stringify(requestBody)
        }).done(function (post) {
            // console.log(post);
            $("#post-feed-timeline").html(Timeline.getHtmlForOnePostForUser(post));
        })
    },

    getPostsForUser: function (id) {
        $.ajax({
            url: Timeline.API_URL + "/post/"+id,
            method: "GET"
        }).done(function (response) {
            // console.log(response);
            // location.replace("/timeline");
            Timeline.displayPostsForOneUser(response.content);
        })
    },

    displayPostsForOneUser: function (posts) {
        let postsHtml = '';

        posts.forEach(post => postsHtml += Timeline.getHtmlForOnePostForUser(post));

        $('#post-feed-timeline').html(postsHtml);
    },

    getHtmlForOnePostForUser: function (post) {
        return `<div class="post-container">
                        <img src="${post.photoUser}" alt="user" class="profile-photo-md pull-left"/>
                        <div class="post-detail">
                            <div class="user-info">
                                <h5><a href="timeline.html" class="profile-link">${post.nameFromUser}</a> <span
                                        class="following">${post.title}</span></h5>
                                <p class="text-muted">${post.date.year} / ${post.date.month} / ${post.date.dayOfMonth}</p> 
                            </div>
                            <div class="reaction" >
                                <a id="save" type="submit" style="display: inline-flex; border: none; background-color: transparent; padding-left: 10px; visibility: hidden" data-postId=${post.id}>
                                <i class="fas fa-save"></i></a>
                                <a id="edit" type="submit" class="buttons"style="display: inline-flex; padding-left: 10px; border: none; background-color: transparent" data-postId=${post.id}>
                                <i class="fa fa-edit"></i>
                                <a id="delete" class="buttons" style="display: inline-flex; padding-left: 30px" data-postId=${post.id}>
                                <i class="fas fa-trash-alt"></i></a>
                             
                            </div>
                            <div class="line-divider"></div>
                            <div class="post-text">
                                <p id="post-content">${post.content}</p>
                                <a id="update-post" data-postId=${post.id} style="visibility: hidden"><input class="update-message" placeholder="Write update to post"></a>
                            </div>
                            <div class="line-divider"></div>
                            <img class="post-photo" src="${post.imageUrl}">
                            <br>
                            <div class="line-divider"></div>
                            <div class="post-comment">
                                <img src=${post.comments.imageUrl} alt="" class="profile-photo-sm"/>
                                <p><a href="timeline.html" class="profile-link">${post.comments.username}</a> ${post.comments.content} <i class="em em-muscle"></i></p>
                            </div>
                            <div class="post-comment">
                                <img src="${post.photoUser}" alt="" class="profile-photo-sm"/>
                                <input type="text" class="form-control" placeholder="Post a comment">
                            </div>
                        </div>
                    </div>`;
    },

    bindEvents: function () {
        $("#btn-publish-timeline").click(function (event) {
            event.preventDefault();
            Timeline.createPostFromTimeline();
        });

        $("#search-icon-timeline").click(function (event) {
            event.preventDefault();

            Timeline.searchPostByTitle();
        });

        $("#post-feed-timeline").delegate("#delete", "click", function (event) {
            event.preventDefault();
            let id = $(this).data("postid");
            Timeline.deletePost(id);
        });

        $("#post-feed-timeline").delegate("#edit", "click", function (event) {
            event.preventDefault();
            let postId = $(this).data("postid");

            Timeline.getPostById(postId);
        });

        $("#post-feed-timeline").delegate("#save", "click", function (event) {
            event.preventDefault();

            let id = $(this).data("postid");
            Timeline.updatePost(id);
        });
    }

};
Timeline.getUserSession();
Timeline.bindEvents();
