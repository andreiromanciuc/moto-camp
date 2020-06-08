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
            // Newsfeed.createPost(response);
        })
    },

    getUserById: function (id) {
        $.ajax({
            url: Newsfeed.API_URL + "/user/" + id,
            method: "GET"
        }).done(function (user) {
            $(".profile-card .profile-photo").html(Newsfeed.displayUserProfilePhoto(user));
            $(".profile-card .profile-name").html(Newsfeed.displayUserName(user));
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
            $(".profile-card .moto-photo").html(Newsfeed.displayMotorProfilePhoto(motor));
            $(".profile-card .moto-name").html(Newsfeed.displayMotorUsername(motor));
        });
    },

    displayMotorProfilePhoto: function (motor) {
        return `<img src="${motor.imageUrl}" alt="moto_img" class="moto-photo">`
    },
    displayMotorUsername: function (motor) {
        return `<h5><a href="#" class="moto-name">${motor.userName}</a></h5>`
    },

    getUserByUsername: function () {
        let username =$("#search").val();

        $.ajax({
            url: Newsfeed.API_URL + "/user/?username="+ username,
            method: "GET",
        }).done(function (user) {
            console.log(user)
        });
    },

    createPost: function (id) {
        let requestBody = {
            title: $("#exampleTextarea1").val(),
            content: $("#exampleTextarea2").val(),
            imageUrl: "",
            userId: id
        };

        $.ajax({
            url: Newsfeed.API_URL + "/post",
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify(requestBody)
        }).done(function (user) {
            console.log(user)
        });

    },

    // getPosts: function () {
    //     $.ajax({
    //         url: Newsfeed.API_URL + "/posts",
    //         method: "GET"
    //     }).done(function (response) {
    //         console.log(response);
    //         Newsfeed.displayPosts(JSON.parse(response).content);
    //     })
    // },
    //
    // displayPosts: function (posts) {
    //     let postsHtml = '';
    //
    //     posts.forEach(posts => postsHtml += Newsfeed.getHtmlForOnePost(posts));
    //
    //     $('.post-content .row').html(postsHtml);
    // },
    //
    // getHtmlForOnePost: function (post) {
    //
    //     return `<div class="post-container">
    //             <img src=${profile.imageURL} alt="user" class="profile-photo-md pull-left" />
    //             <div class="post-detail">
    //               <div class="user-info">
    //                 <h5><a href="timeline.html" class="profile-link">${profile.fullName}</a> <span class="following">following</span></h5>
    //                 <p class="text-muted">${post.dateTime}</p>
    //               </div>
    //               <div class="reaction">
    //                 <a class="btn text-green"><i class="icon ion-thumbsup"></i> 23</a>
    //                 <a class="btn text-red"><i class="fa fa-thumbs-down"></i> 4</a>
    //               </div>
    //               <div class="line-divider"></div>
    //               <div class="post-text">
    //                 <p>${post.content}</p>
    //               </div>
    //               <div class="line-divider"></div>
    //               <div class="post-comment">
    //                 <img src="images/users/user-12.jpg" alt="" class="profile-photo-sm" />
    //                 <p><a href="timeline.html" class="profile-link">Cris </a> Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam <i class="em em-muscle"></i></p>
    //               </div>
    //               <div class="post-comment">
    //                 <img src="images/users/user-1.jpg" alt="" class="profile-photo-sm" />
    //                 <input type="text" class="form-control" placeholder="Post a comment">
    //               </div>
    //             </div>
    //           </div>`;
    // },
    //

    bindEvents: function () {
        $("#search-icon").click(function (event) {
            event.preventDefault();

            Newsfeed.getUserByUsername();

        });

        $("#btn-publish").click(function (event) {
            event.preventDefault();
            Newsfeed.createPost();
        })

    },

};
// Newsfeed.getPosts();
Newsfeed.getUserSession();
Newsfeed.bindEvents();