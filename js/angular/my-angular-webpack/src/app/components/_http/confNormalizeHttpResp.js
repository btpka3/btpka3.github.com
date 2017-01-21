import normalizeHttpRespFactory from "./normalizeHttpRespFactory.js";

function confNormalizeHttpResp($provide) {
    $provide.factory('normalizeHttpResp', normalizeHttpRespFactory)
}
confNormalizeHttpResp.$inject = ['$provide'];

export default confNormalizeHttpResp;
