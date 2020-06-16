
export default function authHeader() {
    const token = localStorage.getItem('token');

    if (token) {
        console.log(token)
        return { Authorization: token };// for Spring Boot back-end
        // return { 'x-access-token': user.accessToken };       // for Node.js Express back-end
    } else {
        return {};
    }
}