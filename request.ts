export type input = {
  panel: string
  port: string
  departures: DeparturesInput[]
}

export type DeparturesInput={
  stopName: string,
  description:string,
  timeLeft: string
	routeColor: string,
	routeTextColor: string,
  minutes: number,
	wheelchairAccessible: boolean,
  online: boolean,
}
