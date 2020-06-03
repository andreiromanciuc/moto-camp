window.Signup = {
    API_URL: "http://localhost:8083/signup",

    matchingPassword: function () {
        $('#sign-password, #repreat-sign-password').on('keyup', function () {
            if ($("#sign-password").val() === $("#repreat-sign-password").val()) {
                $('#message').html('Password is matching').css('color', 'green');
            } else
                $('#message').html('Password is not matching').css('color', 'red');
        });
    },
    registration: function () {
        if ($("#sign-password").val() === $("#repreat-sign-password").val()) {
            let username = $("#sign-name").val();
            let email = $("#sign-email").val();
            let password = $("#sign-password").val();

            $.ajax({
                url: Signup.API_URL + "/user" + "?username=" + username + "&email=" + email + "&password=" + password,
                method: "POST",
            }).done(function (user) {
                let userId = user.id;

                let requestBody = {
                    fullName: "",
                    imageUrl: "",
                    userId: userId
                };

                $.ajax({
                    url: Signup.API_URL + "/profile",
                    method: "POST",
                    contentType: "application/json",
                    data: JSON.stringify(requestBody)
                }).done(function (profile) {
                    window.location.replace("/createMotorcycle");
                })
            })
        }
    },

    bindEvents: function () {
        $("#btn-sign").click(function (event) {
            event.preventDefault();
            Signup.registration();
        });

    }
};
Signup.matchingPassword();
Signup.bindEvents();
