function create_player(player_name, is_host, callback) {
    postToApi('setup/create_player', {
        player_name : player_name,
        is_host : is_host
    }, callback);
}

function delete_player(player_id, callback) {
    postToApi('setup/delete_player', {
        player_id : player_id
    }, callback);
}
