window.Newsfeed = {
    API_URL: "http://localhost:8083/newsfeed",

    getUserSession: function () {
        $.ajax({
            url: Newsfeed.API_URL + "/user/user",
            method: "GET"
        }).done(function (response) {
            let id = response;
            Newsfeed.getUserById(response);
            Newsfeed.getMotorById(response);
        })
    },

    getUserById: function (id) {
        $.ajax({
            url: Newsfeed.API_URL + "/user/" + id,
            method: "GET"
        }).done(function (user) {
            $(".profile-card .profile-photo").html(Newsfeed.displayUserProfilePhoto(JSON.parse(user)));
            $(".profile-card .profile-name").html(Newsfeed.displayUserName(JSON.parse(user)));
        });
    },

    displayUserProfilePhoto: function (user) {
        return `<img src=${user.imageUrl}  alt="user" class="profile-photo"/>`
    },

    displayUserName: function (user) {
        return `<h5><a href="/timeline" class="profile-name" data-id=${user.id}>${user.username}</a></h5>`
    },

    getMotorById: function (id) {
        $.ajax({
            url: Newsfeed.API_URL + "/motorcycle/" + id,
            method: "GET"
        }).done(function (motor) {
            $(".profile-card .moto-photo").html(Newsfeed.displayMotorProfilePhoto(JSON.parse(motor)));
            $(".profile-card .moto-name").html(Newsfeed.displayMotorUsername(JSON.parse(motor)));
        });
    },

    displayMotorProfilePhoto: function (motor) {
        return `<img src="${motor.imageUrl}" alt="moto_img" class="moto-photo">`
    },
    displayMotorUsername: function (motor) {
        return `<h5><a href="#" class="moto-name">${motor.userName}</a></h5>`
    },

    getUserByUsername: function () {
        let username = $("#search").val();

        $.ajax({
            url: Newsfeed.API_URL + "/user/?username=" + username,
            method: "GET",
        }).done(function (user) {
            console.log(user)
        });
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
                console.log(user);
                location.reload();
            });
        });
    },

    searchPostByTitle: function () {
        let title = $("#search").val();
        $.ajax({
            url: Newsfeed.API_URL + "/post/search?title="+title,
            method: "GET"
        }).done(function (response) {
            console.log(response);

        })
    },

    getPosts: function () {
        $.ajax({
            url: Newsfeed.API_URL + "/post",
            method: "GET"
        }).done(function (response) {
            Newsfeed.displayPosts(JSON.parse(response));
        })
    },


    displayPosts: function (posts) {
        let postsHtml = '';

        posts.forEach(post => postsHtml += Newsfeed.getHtmlForOnePost(post));

        $('#post-feed').html(postsHtml);
    },

    getHtmlForOnePost: function (post) {
        return `<div class="post-container" data-id=${post.id}>
                        <img src="${post.photoUser}" alt="user" class="profile-photo-md pull-left"/>
                        <div class="post-detail">
                            <div class="user-info">
                                <h5><a href="timeline.html" class="profile-link">${post.nameFromUser}</a> <span
                                        class="following">${post.title}</span></h5>
                                <p class="text-muted">${post.date}</p>
                            </div>
                            <div class="reaction">
                                <i id="post-delete" class="fa fa-trash" aria-hidden="true"></i>
                            </div>
                            <div class="line-divider"></div>
                            <div class="post-text">
                                <p><i class="em em-thumbsup"></i> <i class="em em-thumbsup"></i>${post.content}</p>
                            </div>
                            <div class="line-divider"></div>
                            <img class="post-photo" src="${post.imageUrl}">
                            <br>
                            <div class="line-divider"></div>
                            <div class="post-comment">
                                <img src="images/users/user-12.jpg" alt="" class="profile-photo-sm"/>
                                <p><a href="timeline.html" class="profile-link">Cris </a> Lorem ipsum dolor sit amet,
                                    consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore
                                    magna aliqua. Ut enim ad minim veniam <i class="em em-muscle"></i></p>
                            </div>
                            <div class="post-comment">
                                <img src="images/users/user-1.jpg" alt="" class="profile-photo-sm"/>
                                <input type="text" class="form-control" placeholder="Post a comment">
                            </div>
                        </div>
                    </div>`;
    },


    bindEvents: function () {
        $("#search-icon").click(function (event) {
            event.preventDefault();

            Newsfeed.getUserByUsername();
            Newsfeed.searchPostByTitle();
        });

        $("#btn-publish").click(function (event) {
            event.preventDefault();
            Newsfeed.createPost();
        })

    },

};
Newsfeed.getPosts();
Newsfeed.getUserSession();
Newsfeed.bindEvents();