const container = document.querySelector(".container"),
  pwShowHide = document.querySelectorAll(".showHidePw"),
  pwFields = document.querySelectorAll(".password"),
  signUp = document.querySelector(".signup-link"),
  login = document.querySelector(".login-link");

//   js code to show/hide password and change icon
pwShowHide.forEach((eyeIcon) => {
  eyeIcon.addEventListener("click", () => {
    pwFields.forEach((pwField) => {
      if (pwField.type === "password") {
        pwField.type = "text";

        pwShowHide.forEach((icon) => {
          icon.classList.replace("uil-eye-slash", "uil-eye");
        });
      } else {
        pwField.type = "password";

        pwShowHide.forEach((icon) => {
          icon.classList.replace("uil-eye", "uil-eye-slash");
        });
      }
    });
  });
});

const logInForm = document.querySelector(".logInForm");
const loginUser = document.querySelector(".loginUser");
const loginPass = document.querySelector(".loginPass");

const errorCredentials = document.querySelector(".error-message");

/*To clear the error message*/
loginUser.addEventListener("focus", function () {
  errorCredentials.classList.add("hidden");
});

loginPass.addEventListener("focus", function () {
  errorCredentials.classList.add("hidden");
});

const logInIntoApp = function () {
  logInForm.addEventListener("submit", (e) => {
    e.preventDefault();

    const username = loginUser.value;
    const password = loginPass.value;

    const checkLogIn = async function () {
      try {
        const response = await fetch("http://localhost:8080/signin", {
          method: "GET",
          headers: {
            "Content-type": "application/json",
            Authorization: "Basic " + btoa(`${username}:${password}`),
          },
        });

        if (response.status === 200) {
          const data = await response.json();

          localStorage.setItem("userToken", data.token);
          localStorage.setItem("userFirstName", data.firstName);
          localStorage.setItem("userId", data.userId);

          window.location.href = "user-profile.html";
        } else {
          errorCredentials.classList.remove("hidden");
        }
      } catch (error) {
        console.log(error.message);
      }
    };

    checkLogIn();
  });
};

logInIntoApp();

//check If Already logged In and send it to home-page
const checkIfAlreadyLoggedIn = function () {
  if (localStorage.getItem("userToken") != null) {
    window.location.href = "home-page.html";
  }
};

checkIfAlreadyLoggedIn();
