window.Newsfeed = {
    API_URL: "http://localhost:8083,",

    getUser: function () {
        Index.logInUsers();
        $.ajax({
            url: Newsfeed.API_URL +"/users"+"?id=1",
            method: "GET"
        }).done(function (response) {
            Newsfeed.displayUser();
        })
    },

    displayUser: function () {
        return `<div class="profile-card">
                    <img src=${profile.imageUrl} data-id=${user.id} alt="user" class="profile-photo"/>
                    <img src=${motorcycle.imageUrl}  data-motoId=${motorcycle.id} alt="moto_img" class="moto-photo">
                    <h5><a href="timeline.html" class="profile-name">${user.username}</a></h5>
                    <h5><a href="#" class="moto-name">${motorcycle.userName}</a></h5>
                </div>`
    },

    getPosts: function () {
        $.ajax({
            url: Newsfeed.API_URL + "/posts",
            method: "GET"
        }).done(function (response) {
            console.log(response);
            Newsfeed.displayPosts(JSON.parse(response).content);
        })
    },

    displayPosts: function (posts) {
        let postsHtml = '';

        posts.forEach(posts => postsHtml += Newsfeed.getHtmlForOnePost(posts));

        $('.post-content .row').html(postsHtml);
    },

    getHtmlForOnePost: function (post) {

        return `<div class="post-container">
                <img src=${profile.imageURL} alt="user" class="profile-photo-md pull-left" />
                <div class="post-detail">
                  <div class="user-info">
                    <h5><a href="timeline.html" class="profile-link">${profile.fullName}</a> <span class="following">following</span></h5>
                    <p class="text-muted">${post.dateTime}</p>
                  </div>
                  <div class="reaction">
                    <a class="btn text-green"><i class="icon ion-thumbsup"></i> 23</a>
                    <a class="btn text-red"><i class="fa fa-thumbs-down"></i> 4</a>
                  </div>
                  <div class="line-divider"></div>
                  <div class="post-text">
                    <p>${post.content}</p>
                  </div>
                  <div class="line-divider"></div>
                  <div class="post-comment">
                    <img src="images/users/user-12.jpg" alt="" class="profile-photo-sm" />
                    <p><a href="timeline.html" class="profile-link">Cris </a> Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam <i class="em em-muscle"></i></p>
                  </div>
                  <div class="post-comment">
                    <img src="images/users/user-1.jpg" alt="" class="profile-photo-sm" />
                    <input type="text" class="form-control" placeholder="Post a comment">
                  </div>
                </div>
              </div>`;
    },

    bindEvents: function () {
        $("#log-out").click(function (event) {
            event.preventDefault();

            window.location.replace("index.html");

        });

    },

};

Newsfeed.getPosts();
Newsfeed.getUser();
Newsfeed.bindEvents();