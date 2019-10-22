# This file is responsible for configuring your application
# and its dependencies with the aid of the Mix.Config module.
#
# This configuration file is loaded before any dependency and
# is restricted to this project.

# General application configuration
use Mix.Config

config :chatserver,
  ecto_repos: [ChatServer.Repo]

# Configures the endpoint
config :chatserver, ChatServerWeb.Endpoint,
  url: [host: "localhost"],
  render_errors: [view: ChatServerWeb.ErrorView, accepts: ~w(html json)],
  secret_key_base: System.get_env("SECRET_KEY_BASE"),
  pubsub: [name: ChatServer.PubSub, adapter: Phoenix.PubSub.PG2]

# Configures Elixir's Logger
config :logger, :console,
  format: "$time $metadata[$level] $message\n",
  metadata: [:request_id]

# Use Jason for JSON parsing in Phoenix
config :phoenix, :json_library, Jason

config :chatserver, ChatServer.Repo,
  database: System.get_env("DB_NAME"),
  username: System.get_env("DB_USER"),
  password: System.get_env("DB_PASS"),
  hostname: System.get_env("DB_HOST"),
  port: System.get_env("DB_PORT")

# Import environment specific config. This must remain at the bottom
# of this file so it overrides the configuration defined above.
import_config "#{Mix.env()}.exs"
