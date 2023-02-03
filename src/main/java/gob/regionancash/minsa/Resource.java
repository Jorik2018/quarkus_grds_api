package gob.regionancash.minsa;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import io.agroal.api.AgroalDataSource;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import gob.regionancash.grds.jpa.MicroRed;
import gob.regionancash.grds.jpa.Red;
import gob.regionancash.grds.mssql.jpa.Disabled;
import gob.regionancash.minsa.jpa.Covid;
import gob.regionancash.minsa.jpa.PPFF;
import gob.regionancash.minsa.jpa.TRegistroDiario;
import gob.regionancash.minsa.jpa.Vaccine;

@Path("")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
public class Resource {

	@Inject
	AgroalDataSource defaultDataSource;
	@Inject
	private CovidService service;

	@GET
	@Path("")
	public Object hello() {
		return 777;
	}

	@GET
	@Path("/{from}/{size}")
	public List hello(@PathParam Integer from, @PathParam Integer size, @QueryParam("query") String filter)
			throws SQLException {
		// http://localhost:8080/admin/desarrollo-social/api/covid/0/10?query=10676301&distinct=1
		List l = new ArrayList();
		System.out.println(from + "->" + size);
		Connection con = null;
		try {
			con = defaultDataSource.getConnection();
			Statement stmt = con.createStatement();
			System.out.println("ini");
			ResultSet rs = stmt.executeQuery(
					"SELECT TOP 20 * FROM BD_COVID.dbo.F100_POSITIVO_14 where num_doc LIKE '%" + filter + "%'");
			while (rs.next()) {
				Covid covid = new Covid();
				covid.setTipoDocumento(rs.getString(1));
				covid.setId(rs.getString(2));
				covid.setApellidoPaterno(rs.getString(3));
				covid.setTipoPrueba(rs.getString(4));
				covid.setFechaEjecucionPrueba(rs.getDate(5));
				covid.setDias(rs.getInt(6));
				covid.setResultado(rs.getString(7));
				covid.setHospitalInstitutoOtros(rs.getString(8));
				covid.setGeresaDiresaDiris(rs.getString(9));
				l.add(covid);
			}
		} finally {
			if (con != null)
				con.close();
			System.out.println("close");
		}
		return l;
	}

	@GET
	@Path("/vaccine/{id}")
	public Object get(@PathParam Integer id) throws SQLException {
		return service.getVaccine(id);
	}

	@DELETE
	@Path("/vaccine/{id}")
	public void destroy(@PathParam Integer id) throws SQLException {
		service.destroy(id);
	}

	@GET
	@Path("/vaccine/{from}/{to}")
	public Object list(@PathParam Integer from, @PathParam Integer to, @QueryParam("query") String filter,
			@QueryParam("datos") String datos, @QueryParam("numDoc") String numDoc,
			@QueryParam("segundaDosis") String segundaDosis, @QueryParam("numCelular") String numCelular,
			@QueryParam("red") String red, @QueryParam("lugarVacunacion") String lugarVacunacion) throws SQLException {
		Map m = new HashMap();
		if (red != null)
			m.put("red", red);
		if (datos != null)
			m.put("datos", datos);
		if (numDoc != null)
			m.put("numDoc", numDoc);
		if (segundaDosis != null)
			m.put("segundaDosis", segundaDosis);
		if (numCelular != null)
			m.put("numCelular", numCelular);
		if (lugarVacunacion != null)
			m.put("lugarVacunacion", lugarVacunacion);

		m.put("data", service.load(from, to, null, m));
		return m;
	}

	@GET
	@Path("/red/{from}/{to}")
	public Object listRed(@PathParam Integer from, @PathParam Integer to, @QueryParam("query") String filter,
			@QueryParam("datos") String datos, @QueryParam("numDoc") String numDoc,
			@QueryParam("segundaDosis") String segundaDosis, @QueryParam("numCelular") String numCelular,
			@QueryParam("red") String red, @QueryParam("lugarVacunacion") String lugarVacunacion) throws SQLException {
		Map m = new HashMap();
		if (red != null)
			m.put("red", red);
		if (datos != null)
			m.put("datos", datos);
		if (numDoc != null)
			m.put("numDoc", numDoc);
		if (segundaDosis != null)
			m.put("segundaDosis", segundaDosis);
		if (numCelular != null)
			m.put("numCelular", numCelular);
		if (lugarVacunacion != null)
			m.put("lugarVacunacion", lugarVacunacion);

		m.put("data",Red.listAll());
		return m;
	}

	@GET
	@Path("/microred/{from}/{to}")
	public Object listMicrored(@PathParam Integer from, @PathParam Integer to, @QueryParam("query") String filter,
			@QueryParam("datos") String datos, @QueryParam("numDoc") String numDoc,
			@QueryParam("segundaDosis") String segundaDosis, @QueryParam("numCelular") String numCelular,
			@QueryParam("red") String red, @QueryParam("lugarVacunacion") String lugarVacunacion) throws SQLException {
		Map m = new HashMap();
		if (red != null)
			m.put("red", red);
		if (datos != null)
			m.put("datos", datos);
		if (numDoc != null)
			m.put("numDoc", numDoc);
		if (segundaDosis != null)
			m.put("segundaDosis", segundaDosis);
		if (numCelular != null)
			m.put("numCelular", numCelular);
		if (lugarVacunacion != null)
			m.put("lugarVacunacion", lugarVacunacion);

		m.put("data",MicroRed.listAll());
		return m;
	}
	
	@POST
    @Path("/disabled")
    public Object saveDisabled(Disabled entity) {
        service.saveDisabled(entity);
        return entity;
    }

	@GET
    @Path("/disabled/{id}")
    public Disabled find(@PathParam("id") Integer id) {
        return service.findDisabled(id);
    }

	@GET
	@Path("/disabled/{from}/{to}")
	public Object listDisabled(@PathParam Integer from, @PathParam Integer to, @QueryParam("query") String filter,
			@QueryParam("datos") String datos,
			@QueryParam("numDoc") String numDoc,
			@QueryParam("code") String code,
			@QueryParam("fullName") String fullName,
			@QueryParam("segundaDosis") String segundaDosis, @QueryParam("numCelular") String numCelular,
			@QueryParam("red") String red, @QueryParam("lugarVacunacion") String lugarVacunacion) throws SQLException {
		Map m = new HashMap();

		if (fullName != null)
			m.put("fullName", fullName); 

		if (code != null)
			m.put("code", code);
		if (red != null)
			m.put("red", red);
		if (datos != null)
			m.put("datos", datos);
		if (numDoc != null){
			m.put("numDoc", numDoc);
			m.put("code", numDoc);
		}
		if (segundaDosis != null)
			m.put("segundaDosis", segundaDosis);
		if (numCelular != null)
			m.put("numCelular", numCelular);
		if (lugarVacunacion != null)
			m.put("lugarVacunacion", lugarVacunacion);

		m.put("data", service.loadDisabled(from, to, null, m));
		return m;
	}

	@GET
	@Path("/disabled/certificate/{from}/{to}")
	@Produces(MediaType.APPLICATION_JSON)
	public Object listDisabledCertificate(@PathParam Integer from, @PathParam Integer to, @QueryParam("query") String filter,
			@QueryParam("datos") String datos, @QueryParam("numDoc") String numDoc,
			@QueryParam("segundaDosis") String segundaDosis, @QueryParam("numCelular") String numCelular,
			@QueryParam("red") String red, @QueryParam("lugarVacunacion") String lugarVacunacion) throws SQLException {
		Map m = new HashMap();
		if (red != null)
			m.put("red", red);
		if (datos != null)
			m.put("datos", datos);
		if (numDoc != null)
			m.put("numDoc", numDoc);
		if (segundaDosis != null)
			m.put("segundaDosis", segundaDosis);
		if (numCelular != null)
			m.put("numCelular", numCelular);
		if (lugarVacunacion != null)
			m.put("lugarVacunacion", lugarVacunacion);

		m.put("data", service.loadDisabledCertificate(from, to, null, m));
		return m;
	}





	@POST
	@Path("/vaccine")
	@Consumes({ MediaType.APPLICATION_JSON })
	public Object create(Vaccine entity) {
		service.edit(entity);
		return entity.getId();
	}

	@GET
	@Path("/vaccine-covid/{from}/{to}")
	@Produces(MediaType.APPLICATION_JSON)
	public Object list(@PathParam Integer from, @PathParam Integer to, @QueryParam("query") String query,
			@QueryParam("numDoc") String numDoc) throws SQLException {
		Map m = new HashMap();
		if (query != null)
			m.put("query", query);
		if (numDoc != null)
			m.put("numDoc", numDoc);
		m.put("data", service.loadVaccineCOVID(from, to, null, m));
		return m;
	}

	@Inject
	private PPFFService PPFFservice;

	@GET
	@Path("/ppff/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getPPFF(@PathParam Integer id) throws SQLException {
		return PPFFservice.get(id);
	}

	@DELETE
	@Path("/ppff/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public void destroyPPFF(@PathParam Integer id) throws SQLException {
		PPFFservice.destroy(id);
	}

	@GET
	@Path("/ppff/{from}/{to}")
	@Produces(MediaType.APPLICATION_JSON)
	public Object listPPFF(@PathParam Integer from, @PathParam Integer to, @QueryParam("query") String filter,
			@QueryParam("datos") String datos, @QueryParam("numDoc") String numDoc,
			@QueryParam("segundaDosis") String segundaDosis, @QueryParam("numCelular") String numCelular,
			@QueryParam("red") String red, @QueryParam("lugarVacunacion") String lugarVacunacion) throws SQLException {
		Map m = new HashMap();
		if (red != null)
			m.put("red", red);
		if (datos != null)
			m.put("datos", datos);
		if (numDoc != null)
			m.put("numDoc", numDoc);
		if (segundaDosis != null)
			m.put("segundaDosis", segundaDosis);
		if (numCelular != null)
			m.put("numCelular", numCelular);
		if (lugarVacunacion != null)
			m.put("lugarVacunacion", lugarVacunacion);

		m.put("data", PPFFservice.load(from, to, null, m));
		return m;
	}

	@POST
	@Path("/ppff")
	@Consumes({ MediaType.APPLICATION_JSON })
	public Object create(PPFF entity) {
		PPFFservice.edit(entity);
		return entity.getId();
	}

	@Inject
	private TRegistroDiarioService TRegistroDiarioService;

	@GET
	@Path("/dailyRecord/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getTRegistroDiario(@PathParam Integer id) throws SQLException {
		return TRegistroDiarioService.get(id);
	}

	@DELETE
	@Path("/dailyRecord/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public void destroyTRegistroDiario(@PathParam Integer id) throws SQLException {
		TRegistroDiarioService.destroy(id);
	}

	@GET
	@Path("/dailyRecord/{from}/{to}")
	@Produces(MediaType.APPLICATION_JSON)
	public Object listTRegistroDiario(@PathParam Integer from, @PathParam Integer to,
			@QueryParam("query") String filter, @QueryParam("datos") String datos, @QueryParam("dniNotificante") String dniNotificante,
			@QueryParam("segundaDosis") String segundaDosis, @QueryParam("numCelular") String numCelular,
			@QueryParam("red") String red, @QueryParam("lugarVacunacion") String lugarVacunacion) throws SQLException {
		Map m = new HashMap();
		if (red != null)
			m.put("red", red);
		if (datos != null)
			m.put("datos", datos);
		if (dniNotificante != null)
			m.put("dniNotificante", dniNotificante);
		if (segundaDosis != null)
			m.put("segundaDosis", segundaDosis);
		if (numCelular != null)
			m.put("numCelular", numCelular);
		if (lugarVacunacion != null)
			m.put("lugarVacunacion", lugarVacunacion);

		m.put("data", TRegistroDiarioService.load(from, to, null, m));
		return m;
	}

	@POST
	@Path("/dailyRecord")
	@Consumes({ MediaType.APPLICATION_JSON })
	public Object create(TRegistroDiario entity) {
		TRegistroDiarioService.edit(entity);
		return entity.getId();
	}

	@POST
	@Path("/ppff/download")
	@Consumes({ MediaType.APPLICATION_JSON })
	public Object download(Map mm) throws IOException {
		try {
			List data = (List) PPFFservice.download(mm);
			
			String fileName = "tmp.jao";
			File file=new File(File.createTempFile("temp-file-name", ".tmp").getParentFile(), fileName);
			saveObject(file.getAbsolutePath(),data);

			//MultipartFormDataOutput output = new MultipartFormDataOutput();
			//output.addFormData("file", file, MediaType.APPLICATION_OCTET_STREAM_TYPE);
			
			InputStream inputStream = new FileInputStream(file);
			InputStream is2 = ClientBuilder.newClient().target("http://localhost:8181/xls/api/export").request()
					
					.post(Entity.entity(inputStream, MediaType.APPLICATION_OCTET_STREAM), InputStream.class);
			fileName = "ppff.xlsx";

			return Response.ok((StreamingOutput) (java.io.OutputStream output) -> {
				try {
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					int len;
					byte[] buffer = new byte[4096];
					while ((len = is2.read(buffer, 0, buffer.length)) != -1) {
						output.write(buffer, 0, len);
					}
					output.flush();
					is2.close();
				} catch (IOException e) {
					throw new WebApplicationException("File Not Found !!", e);
				}
			}, MediaType.APPLICATION_OCTET_STREAM).header("content-disposition", "attachment; filename = " + fileName)
					.build();

		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static void saveObject(String filename, Object object, Object... extra) throws IOException {
		writeObject(getFile(filename), object, extra);
	}

	public static File writeObject(File filename, Object object, Object... extra) throws IOException {
//      X.log("filenamefilenamefilename=="+filename);
		FileOutputStream fileOut = null;
		BufferedOutputStream bufferedOut = null;
		ObjectOutputStream obOut = null;
		try {
			fileOut = new FileOutputStream(filename);
			bufferedOut = new BufferedOutputStream(fileOut);
			obOut = new ObjectOutputStream(bufferedOut);
			obOut.writeObject(object);
		} finally {
			try {
				if (bufferedOut != null) {
					bufferedOut.close();
				}
				if (obOut != null) {
					obOut.close();
				}
				if (fileOut != null) {
					fileOut.close();
				}
			} catch (IOException e) {
			}
		}
		return filename;
	}

	public static File getFile(String name) {
		return getFile(name, true);
	}

	public static File getFile(String name, boolean create) {
		String fileSeparator = System.getProperty("file.separator");
		return getFile(new File(
				// System.getProperty("user.home") + fileSeparator +
				// System.getProperty(X.USER_HOME_DIR) + fileSeparator +

				name), create, name.lastIndexOf(".") > name.lastIndexOf(fileSeparator));
	}

	public static File getFile(File file, boolean create, boolean isFile) {
		if (!create && !file.exists()) {
			return null;
		}
		if (!isFile) {
			file.mkdirs();
		} else {
			try {
				File parent = new File(file.getParent());
				if (!parent.exists()) {
					parent.mkdirs();
				}
				if (!file.isFile()) {
					// file.delete();
				}
				if (!file.exists()) {
					file.createNewFile();
				}
			} catch (IOException exception) {
				throw new RuntimeException(exception);
////                X.log(exception);
			}
		}
		if (create && !file.exists())
			throw new RuntimeException("No se pudo crear '" + file + "'");
		return file;
	}

}