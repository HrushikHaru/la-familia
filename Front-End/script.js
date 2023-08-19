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

// js code to appear signup and login form
signUp.addEventListener("click", () => {
  container.classList.add("active");
});
login.addEventListener("click", () => {
  container.classList.remove("active");
});

//SignUp and LogIn
const firstName = document.querySelector(".firstName");
const lastName = document.querySelector(".lastName");
const dateOfBirth = document.querySelector(".dateOfBirth");
//flatpickr date
flatpickr(dateOfBirth, { dateFormat: "dMY" });
const username = document.querySelector(".username");
const passwordCheck = document.querySelector(".pass");
const password = document.querySelector(".confirmPass");
const signUpForm = document.querySelector(".signUpForm");

const storeSignUpInfoToDB = function () {
  signUpForm.addEventListener("submit", function (e) {
    e.preventDefault();

    const getYear = Number(dateOfBirth.value.substring(5));
    const currentDate = new Date();
    const age = currentDate.getFullYear() - getYear;
    const name = firstName.value;
    const surname = lastName.value;
    const birth = dateOfBirth.value.toUpperCase();
    const user = username.value;
    const pass = password.value;

    if (password.value != passwordCheck.value) {
      showModalAndOverlay(
        "Password Mismatch!",
        "The passwords in both the context aren't matching. Please verify."
      );
    } else {
      const logIn = async function () {
        try {
          const response = await fetch("http://localhost:8080/signup", {
            method: "POST",
            headers: {
              "Content-type": "application/json",
            },
            body: JSON.stringify({
              firstName: name,
              lastName: surname,
              dateOfBirth: birth,
              age: age,
              userName: user,
              passWord: pass,
            }),
          });

          console.log(response.status);

          if (response.status === 201) {
            window.location.href = "login-verify.html";
          }
        } catch (error) {
          console.log(error.message);
        }
      };

      logIn();
    }
  });
};

storeSignUpInfoToDB();

//Open and Close Modal
const modal = document.querySelector(".modal");
const overlay = document.querySelector(".overlay");
const modalClose = document.querySelector(".modalClose");
const validation = document.querySelector(".validation");
const errorMessage = document.querySelector(".validation-desc");

const errorCredentials = document.querySelector(".error-message");

modalClose.addEventListener("click", function () {
  modal.classList.add("hidden");
  overlay.classList.add("hidden");
});

function showModalAndOverlay(validationMsg, validationDesc) {
  validation.textContent = "";
  errorMessage.textContent = "";

  validation.textContent = validationMsg;
  errorMessage.textContent = validationDesc;

  modal.classList.remove("hidden");
  overlay.classList.remove("hidden");
}

//Login Form
const logInForm = document.querySelector(".logInForm");
const loginUser = document.querySelector(".loginUser");
const loginPass = document.querySelector(".loginPass");

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

          window.location.href = "home-page.html";
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
