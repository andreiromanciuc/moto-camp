window.Newsfeed = {
    API_URL: "http://localhost:8083/newsfeed",

    getUserSession: function () {
        $.ajax({
            url: Newsfeed.API_URL + "/user/user",
            method: "GET"
        }).done(function (response) {
            // console.log(response);
            Newsfeed.getUserById(response);
            Newsfeed.getMotorById(response);
        })
    },

    getUserById: function (id) {
        $.ajax({
            url: Newsfeed.API_URL + "/user/" + id,
            method: "GET"
        }).done(function (user) {
            // console.log(user);
            $(".profile-photo").html(Newsfeed.displayUserProfilePhoto(user));
            $(".profile-name").html(Newsfeed.displayUserName(user));
            $(".photo-form").html(Newsfeed.displayUsersProfilePhotoToFormGroup(user));

        });
    },

    displayUsersProfilePhotoToFormGroup: function (user) {
        return `<img src=${user.imageUrl} alt="" class="profile-photo-md"/>`
    },
    displayUserProfilePhoto: function (user) {
        return `<img src=${user.imageUrl}  alt="user" class="profile-photo"/>`
    },

    displayUserName: function (user) {
        return `<h5><a href="" class="profile-name" id="username" data-id=${user.id}>${user.username}</a></h5>`
    },

    getMotorById: function (id) {
        $.ajax({
            url: Newsfeed.API_URL + "/motorcycle/" + id,
            method: "GET"
        }).done(function (motor) {
            $(".moto-photo").html(Newsfeed.displayMotorProfilePhoto(motor));
            $(".moto-name").html(Newsfeed.displayMotorUsername(motor));
        });
    },

    displayMotorProfilePhoto: function (motor) {
        return `<img src="${motor.imageUrl}" alt="moto_img" class="moto-photo">`
    },

    displayPhotoOfMotorSearchedUser: function (user) {
        return `<img src="${user.motorPhoto}" alt="moto_img" class="moto-photo">`
    },
    displayMotorUsername: function (motor) {
        return `<h5><a href="#" class="moto-name">${motor.userName}</a></h5>`
    },

    displayUsernameOfMotorSearchedUser: function (user) {
        return `<h5><a href="#" class="moto-name">${user.motorUsername}</a></h5>`
    },

    searchUserByUsername: function () {
        let username = $("#search").val();

        $.ajax({
            url: Newsfeed.API_URL + "/user/?username=" + username,
            method: "GET"
        }).done(function (user) {
            // console.log(user);
            $('#post-feed').html(Newsfeed.displayUserAfterSearch(user));
        });

    },

    getUserAfterSearchByUsername: function (username) {
        $.ajax({
            url: Newsfeed.API_URL + "/user/username/" + username,
            method: "GET"
        }).done(function (user) {
            console.log(user);
            let id = user.id;
            Newsfeed.getPostsForUser(id);
            $(".profile-photo").html(Newsfeed.displayUserProfilePhoto(user));
            $(".profile-name").html(Newsfeed.displayUserName(user));
            $(".photo-form").html(Newsfeed.displayUsersProfilePhotoToFormGroup(user));
            $(".moto-photo").html(Newsfeed.displayPhotoOfMotorSearchedUser(user));
            $(".moto-name").html(Newsfeed.displayUsernameOfMotorSearchedUser(user));

        });
    },

    getPostsForUser: function (id) {
        $.ajax({
            url: Newsfeed.API_URL + "/post/" + id,
            method: "GET"
        }).done(function (response) {
            // console.log(response);
            Newsfeed.displayPosts(response.content);
        })
    },


    displayUserAfterSearch: function (user) {
        return `<div class="post-container">
                        <img src="${user.imageUrl}" alt="user" class="profile-photo-md pull-left"/>
                        <div class="post-detail">
                            <div class="user-info">
                                <h5><a href="#" id="searched_user" class="profile-link">${user.username}</a></h5>
                            </div>
                        </div>
                    </div>`
    },

    createPost: function () {
        $.ajax({
            url: Newsfeed.API_URL + "/user/user",
            method: "GET"
        }).done(function (response) {

            let requestBody = {
                title: $("#exampleTextarea1").val(),
                content: $("#exampleTextarea2").val(),
                imageUrl: "",
                userId: response
            };

            $.ajax({
                url: Newsfeed.API_URL + "/post",
                method: "POST",
                contentType: "application/json",
                data: JSON.stringify(requestBody)
            }).done(function (user) {
                // console.log(user);
                location.reload();
            });
        });
    },

    searchPostByTitle: function () {
        let title = $("#search").val();
        $.ajax({
            url: Newsfeed.API_URL + "/post/search?title=" + title,
            method: "GET"
        }).done(function (post) {
            $("#post-feed").html(Newsfeed.getHtmlForOnePost(post));
        })
    },

    createComment: function () {
        let requestBody = {
            content: $(".form-control").val(),
            postId: $(this).id
        };

        $.ajax({
            url: Newsfeed.API_URL + "/comment",
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify(requestBody)
        }).done(function (comment) {
            console.log(comment)
        });
    },


    getPosts: function () {
        $.ajax({
            url: Newsfeed.API_URL + "/post",
            method: "GET"
        }).done(function (response) {
            Newsfeed.displayPosts(response.content);
        })
    },


    displayPosts: function (posts) {
        let postsHtml = '';

        posts.forEach(post => postsHtml += Newsfeed.getHtmlForOnePost(post));

        $('#post-feed').html(postsHtml);
    },

    getHtmlForOnePost: function (post) {
        return `<div class="post-container">
                        <img src="${post.photoUser}" alt="user" class="profile-photo-md pull-left"/>
                        <div class="post-detail">
                            <div class="user-info">
                                <h5><a href="" class="profile-link">${post.nameFromUser}</a> <span
                                        class="following">${post.title}</span></h5>
                                <p class="text-muted">${post.date.year} / ${post.date.month} / ${post.date.dayOfMonth}</p> 
                            </div>
                            <div class="reaction" >
                            
                            </div>
                            <div class="line-divider"></div>
                            <div class="post-text">
                                                            
                                <p id="post-content">${post.content}</p>
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
                                <button class="comment-button" style="background-color: dodgerblue; border: none; border-radius: 10px;
                                        color: white">post</button>                            
                            </div>
                        </div>
                    </div>`;
    },

    bindEvents: function () {
        $("#search-icon").click(function (event) {
            event.preventDefault();

            Newsfeed.searchUserByUsername();
            Newsfeed.searchPostByTitle();
        });

        $("#btn-publish").click(function (event) {
            event.preventDefault();
            Newsfeed.createPost();
        });

        $(".profile-name").delegate("#username", "click", function (event) {
            event.preventDefault();
            location.replace("/timeline");
        });

        $('#post-feed').delegate('#searched_user', 'click', function (event) {
            event.preventDefault();
            let username = $('#searched_user').clone().children().remove().end().text();
            Newsfeed.getUserAfterSearchByUsername(username);
        });

        $('#post-feed').delegate('.comment-button', 'click', function (event) {
            event.preventDefault();
            Newsfeed.createComment();
        });
    },

};
Newsfeed.getPosts();
Newsfeed.getUserSession();
Newsfeed.bindEvents();