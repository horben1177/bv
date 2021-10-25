DROP TABLE IF EXISTS BV_HISTORY;

CREATE TABLE BV_HISTORY(
    id INT AUTO_INCREMENT PRIMARY KEY,
    payload VARCHAR(500),
    creationDate DATE
);

INSERT INTO BV_HISTORY(payload, creationDate) VALUES
('{
    freq_word: incendat,
    avg_paragraph_size: 111,
    avg_paragraph_processing_time: 0 ms,
    total_processing_time: 1 ms
}', CURRENT_DATE());