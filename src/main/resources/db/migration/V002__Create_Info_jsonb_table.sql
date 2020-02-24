CREATE TABLE info_jsonb (
  id    SERIAL NOT NULL PRIMARY KEY,
  json  JSONB NOT NULL
);
-- CREATE INDEX ON info_jsonb((json->>'customerId'));

CREATE INDEX info_jsonb_customerId_idx ON info_jsonb (((json ->> 'customerId')::int))
WHERE (json ->> 'customerId') IS NOT NULL;