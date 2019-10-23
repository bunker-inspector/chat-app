defmodule ChatServer.Repo do
  use Ecto.Repo,
    otp_app: :chatserver,
    adapter: Ecto.Adapters.Postgres

  use Scrivener,
    page_size: 20
end
